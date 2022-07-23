package com.gaurav.quoraapp.Service.impl;
import com.gaurav.quoraapp.Dto.UserDto;
import com.gaurav.quoraapp.Repo.ExpertiseRepo;
import com.gaurav.quoraapp.Repo.QuestionAnswerRepo;
import com.gaurav.quoraapp.Repo.RoleRepo;
import com.gaurav.quoraapp.Repo.UsersRepo;
import com.gaurav.quoraapp.Service.UserService;
import com.gaurav.quoraapp.model.Expertise;
import com.gaurav.quoraapp.model.QuestionAndAnswer;
import com.gaurav.quoraapp.model.Roles;
import com.gaurav.quoraapp.model.Users;
import com.gaurav.quoraapp.security.AesConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ExpertiseRepo expertiseRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    QuestionAnswerRepo questionAndAnswer;
    @Autowired
    AesConverter aesConverter;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepo.findUsersByEmail(email);
        if (users == null) {
            throw new UsernameNotFoundException("user not found");
        } else {
            log.info("User found in db {}" + email);
        }
        Collection< SimpleGrantedAuthority > authorities = new ArrayList<>();
       users.getRoles().forEach(roles -> {
           authorities.add(new SimpleGrantedAuthority(roles.getRole()));
       });
        return new User(users.getEmail(), users.getPassword(), authorities);
    }

    @Override
    public Users createUser(Users users) {
        List<Roles> roles = new ArrayList<>();
        List<Expertise> expertise = new ArrayList<>();
        List<QuestionAndAnswer> questionAndAnswers=new ArrayList<>();
        users.getRoles().addAll(roles);
        users.getExpertise().addAll(expertise);
        users.getQuestionAndAnswers().addAll(questionAndAnswers);
        try {
            users.setEmail(aesConverter.encrypt(users.getEmail(), "secretkey"));
        } catch (Exception e) {

        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersRepo.save(users);

       /* // save that user
        Users users = new Users();
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setEmail(aesConverter.encrypt(userDto.getEmail(), "secretkey"));
        users.setName(userDto.getName());
        users.setABoolean(userDto.getGetABoolean());
        users.setDob(userDto.getDob());

        Users savedUser = usersRepo.save(users);*/

      /*  // now set roles
        for(Roles roles1: userDto.getRoles()){
            Roles roles2  = new Roles();
            roles2.setRole(roles1.getRole());
           // roles2.setUsers(users);
            roleRepo.save(roles2);
        }*/

      /*  //save role
        for(Expertise expertise1: userDto.getExpertise()){
            Expertise expertise2  = new Expertise();
            expertise2.setExpertise(expertise1.getExpertise());
            expertise2.setUser(users);
            expertiseRepo.save(expertise2);
        }*/





//        users.getExpertise().addAll(expertise);
//        users.getRoles().addAll(roles);
        //Saving roles
       /* for (Roles roles1:users.getRoles()){
            Roles roless = new Roles();
            roless.setRole(roles1.getRole());
            roleRepo.save(roless);
        }
*/

    }

    @Override
    public List< UserDto > getUserByDto() {
        return null;
    }
    // public  static void  expertiseToDto(Expertise expertise)

 /*   @Override
    public List< UserDto > getUserByDto() {
        List<Users> usersList = usersRepo.fetchAll();
        System.out.println(usersList);
        List<UserDto> userDtoList = new ArrayList<>();
        for(Users users: usersList){
            UserDto userDto = new UserDto();
            userDto.setName(users.getName());
            userDtoList.add(userDto);
        }
        return userDtoList;
        //return usersRepo.findAll().stream().map(this::userDto).collect(Collectors.toList());
    }*/

   /* public UserDto userDto(Users users) {
        return UserDto.builder()
                .name(users.getName())
                .roles(roleRepo.fetchRolesFromUserId(users.getUid()))
                .expertise(expertiseRepo.fetchExpertiseById(users.getUid())).build();
    }*/

    @Override
    public List< Users > getAllUser() {
          Users users=new Users();
        users.setEmail(aesConverter.decrypt(users.getEmail(),"secret"));
        return usersRepo.findAll();
    }

    @Override
    public Users getUserById(long id) {
        return usersRepo.getById(id);
    }

    @Override
    public Users getUserByEmail(String email) {
        log.info("fetching user {} database", email);
        return usersRepo.findUsersByEmail(email);
    }

    @Override
    public Users updateUser(Users users, long id) {
        Users existingUser = usersRepo.findById(id).orElseThrow();
        existingUser.setName(users.getName());
        existingUser.setEmail(users.getEmail());
        existingUser.setPassword(users.getPassword());
        //existingUser.setRoles(roleRepo.fetchRolesFromUserId(users.getUid()));
        usersRepo.save(existingUser);
        return existingUser;
    }

    @Override
    public void deleteUser(long id) {
        usersRepo.findById(id).orElseThrow();
        usersRepo.deleteById(id);
    }

    @Override
    public Roles saveRole(Roles roles) {
        return roleRepo.save(roles);
    }



}
