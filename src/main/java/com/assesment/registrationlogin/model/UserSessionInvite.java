package com.assesment.registrationlogin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_session_invite")
public class UserSessionInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "invite_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "UserSessionInvite_user_ref"))
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private LunchSession lunchSession;

    private boolean userAcceptInvitation = false;

    @ManyToOne(optional = true)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;
}
