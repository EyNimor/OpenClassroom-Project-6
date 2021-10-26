package com.openclassroom.paymybuddy.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.openclassroom.paymybuddy.annotation.ExcludeFromJacocoGeneratedReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@Entity @Table(name = "users") @ExcludeFromJacocoGeneratedReport
public class User implements Serializable {

    @Id
    private String email;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "iban")
    private String iban;
    @Column(name = "wallet")
    private float wallet;

    public User(String email, String firstName, String lastName, String password) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
    }
    
}
