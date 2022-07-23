package com.gaurav.quoraapp.Repo;

import com.gaurav.quoraapp.model.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpertiseRepo extends JpaRepository< Expertise, Long > {
  /*  @Query(value = "select * from users_expertise ue where ue.user_exp_fk=:uid",nativeQuery = true)
    List<Expertise> fetchExpertiseById(Long uid);*/
}
