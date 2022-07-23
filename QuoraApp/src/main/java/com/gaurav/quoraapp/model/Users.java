package com.gaurav.quoraapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;


    @Column(name = "uname")
    private String name;


    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Lob
    @Column(name = "passwords", nullable = false)
    private String password;


    @Column(name = "enabled")
    private Byte aBoolean;


    @Column(name = "phone")
    private BigDecimal phoneNumber;


    @Column(name = "dob", nullable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dob;

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "expertIn", nullable = false)

    private List< Expertise > expertise;

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)

    @Column(name = "roles")
    private List< Roles > roles;

    @Column(name = "question_answer")
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)

    private List<QuestionAndAnswer> questionAndAnswers;

}
