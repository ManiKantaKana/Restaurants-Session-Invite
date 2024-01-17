package com.assesment.registrationlogin.repository;

import com.assesment.registrationlogin.model.UserSessionInvite;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Transactional
@Repository
public interface UserSessionInviteRepository extends JpaRepository<UserSessionInvite,Long>  {

    @Query(value = "SELECT * FROM users.user_session_invite WHERE session_id = ?1", nativeQuery = true)
    List<UserSessionInvite> findAllBySessionId(Long sessionId);

    @Modifying
    @Query(value = "update users.user_session_invite set user_accept_invitation = 1 WHERE id = ?1", nativeQuery = true)
    void acceptInvitation(Long userSessionInviteId);
    @Modifying
    @Query(value = "update users.user_session_invite set restaurant_id = ?2 WHERE id = ?1", nativeQuery = true)
    void updateRestaurant(Long userSessionInviteId, Long restaurantId);
}
