package com.project.socialnetwork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomFriends {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long RequestID;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "reciverId")
    private User reciverId;

    @Enumerated(EnumType.STRING)
    private RequestStaus requestStatus;

    private LocalDate friendSince;

}

