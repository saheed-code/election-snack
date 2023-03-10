package com.paragon.poll.data.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Address address;
    private String nin;
    private String profileImage;
    private String password;
    private String createdAt;
    private Boolean isApproved;
}
