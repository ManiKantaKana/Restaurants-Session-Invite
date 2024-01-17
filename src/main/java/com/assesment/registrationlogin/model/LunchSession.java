package com.assesment.registrationlogin.model;

import jakarta.persistence.*;
import com.assesment.registrationlogin.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lunch_session")
public class LunchSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionName;
    private boolean ended = false;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "LunchSession_user_ref"))
    private User user;

    public LunchSession(Long id) {
        this.id=id;
    }
}
