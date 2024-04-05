package com.project.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class CustomFriends extends FriendsList {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long RequestID;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    public CustomFriends() {
        super();
    }

}

