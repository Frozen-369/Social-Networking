package com.project.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long profile_id;
    private String firstname;
    private String lastname;
    private String bio;
    private String profile_pic_url;
    private String address;
    private String birthday;

    @OneToOne
    @JoinColumn(name = "user")
    private User user;


}
