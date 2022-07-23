package com.gaurav.quoraapp.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "expertise")
public class Expertise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exid")
    private Long id;

    @Column(name = "expertise")
    private String expertise;


}
