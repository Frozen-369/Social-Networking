package com.project.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profile_id;
    private String firstname;
    private String lastname;
    private String bio;
    private String profile_pic_url;
    private String address;
    private String birthday;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
