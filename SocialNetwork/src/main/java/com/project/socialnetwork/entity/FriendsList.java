package com.project.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor

@Setter
@Getter
@NoArgsConstructor
public class FriendsList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @Column(name="status")
    private RequestStatus status;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private User requestId;

    @Column(name = "friends_since")
    private LocalDate friendsSince;
}
