package com.example.exchange.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long user_id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

}
