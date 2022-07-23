package com.gaurav.quoraapp.Repo;

import com.gaurav.quoraapp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepo extends JpaRepository< Users, Long > {

    Users findUsersByEmail(String email);

 /*   @Query(value = "select * from users", nativeQuery = true)
    List<Users> fetchAll();*/
}
