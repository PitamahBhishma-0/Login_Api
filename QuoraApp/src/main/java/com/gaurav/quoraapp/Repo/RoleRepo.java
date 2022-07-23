package com.gaurav.quoraapp.Repo;

import com.gaurav.quoraapp.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository< Roles, Long > {
    /*@Query(value = "select * from roles where roles.user_role=:uid", nativeQuery = true)
    List<Roles> fetchRolesFromUserId(Long uid);*/
}
