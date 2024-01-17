package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.UserSessionInvite;
import com.assesment.registrationlogin.web.dto.RestaurantDto;
import com.assesment.registrationlogin.web.dto.UserDto;

import java.util.List;

public interface RestaurantService {
    List<RestaurantDto> listAllRestaurants();
}
