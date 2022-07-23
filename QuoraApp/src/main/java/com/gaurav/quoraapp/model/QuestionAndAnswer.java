package com.gaurav.quoraapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "queans")
public class QuestionAndAnswer {
    @Id
    @Column(name = "qaid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaId;


    @Column(name = "que", nullable = false)
    private String question;


    @Column(name = "ans", nullable = true)
    private String answer;





}
