package com.gaurav.quoraapp.controller;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gaurav.quoraapp.Dto.UserDto;
import com.gaurav.quoraapp.Repo.RoleRepo;
import com.gaurav.quoraapp.Service.UserService;
import com.gaurav.quoraapp.model.Roles;
import com.gaurav.quoraapp.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepo roleRepo;

    @PostMapping("/saveUsers")
    public ResponseEntity< Object > saveUsers(@RequestBody Users users) {
        return new ResponseEntity< Object >(userService.createUser(users), HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity< Object > getAllUser() {

        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }


    @GetMapping("/getByDto")
    public ResponseEntity< Object > getUserByDTO() {
        return new ResponseEntity<>(userService.getUserByDto(), HttpStatus.OK);
    }

    @PostMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable("id") long id,@RequestBody Users users)
    {
          return new ResponseEntity<Users>(userService.updateUser(users,id),HttpStatus.OK);
    }



    @GetMapping("/token/refresh")

    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String email = decodedJWT.getSubject();
                Users users = userService.getUserByEmail(email);
                String accessToken = JWT.create().withSubject(users.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", users.getRoles().stream().map(Roles::getRole)
                                .collect(Collectors.toList()))
                        .sign(algorithm);


 //response.setHeader("accessToken",accessToken);
        response.setHeader("refreshToken",refreshToken);

                Map< String, String > tokens = new HashMap<>();
                //tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception e) {
                log.error("error in logging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map< String, String > error = new HashMap<>();
                error.put("error-message", e.getMessage());
                //tokens.put("refreshToken",refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }
        } else {
            throw new RuntimeException("Error refresh token missing");
        }
    }

}
