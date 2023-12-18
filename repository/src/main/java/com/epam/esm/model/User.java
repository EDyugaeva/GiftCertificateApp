package com.epam.esm.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Order> orderList;
}
