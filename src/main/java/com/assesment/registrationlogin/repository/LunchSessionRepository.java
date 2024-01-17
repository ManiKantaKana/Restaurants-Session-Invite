package com.assesment.registrationlogin.repository;

import com.assesment.registrationlogin.model.LunchSession;
import com.assesment.registrationlogin.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface LunchSessionRepository extends JpaRepository<LunchSession,Long>  {
    @Query(value = "SELECT * FROM users.lunch_session l WHERE l.user_id = ?1", nativeQuery = true)
    List<LunchSession> findAllByUser(Long id);

    @Modifying
    @Query(value = "update users.lunch_session set ended = 1 WHERE id = ?1", nativeQuery = true)
    void endSession(Long id);
}
