package com.aoikaze.bs.demos.web;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phone;

}
