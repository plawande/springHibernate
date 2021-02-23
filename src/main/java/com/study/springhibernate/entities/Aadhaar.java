package com.study.springhibernate.entities;

import javax.persistence.*;

@Entity
@Table(name="person_aadhaar")
public class Aadhaar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number")
    private String number;
}
