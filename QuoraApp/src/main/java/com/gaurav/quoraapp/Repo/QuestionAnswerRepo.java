package com.gaurav.quoraapp.Repo;

import com.gaurav.quoraapp.model.QuestionAndAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionAnswerRepo extends JpaRepository< QuestionAndAnswer, Long > {

}
