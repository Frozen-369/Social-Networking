package com.project.socialnetwork.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "UserInfo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String username;
    private String password;
    private String email;
    private String phoneNum;

    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "friend")
    private List<FriendsList> friendId;

    public void removeFriend(User friend) {
        friendId.removeIf(f -> f.getFriendId().equals(friend));
        friend.getFriendId().removeIf(f -> f.getFriendId().equals(this));
    }

}
