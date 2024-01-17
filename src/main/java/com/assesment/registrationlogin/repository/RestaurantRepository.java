package com.assesment.registrationlogin.repository;

import com.assesment.registrationlogin.model.LunchSession;
import com.assesment.registrationlogin.model.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long>  {

}
