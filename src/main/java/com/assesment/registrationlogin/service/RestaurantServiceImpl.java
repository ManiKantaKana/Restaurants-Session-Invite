package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.repository.RestaurantRepository;
import com.assesment.registrationlogin.repository.UserSessionInviteRepository;
import com.assesment.registrationlogin.web.dto.RestaurantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("restaurantService")
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<RestaurantDto> listAllRestaurants() {
        List<RestaurantDto> restaurantList = restaurantRepository.findAll()
                .stream()
                .map(r -> RestaurantDto.builder()
                        .id(r.getId())
                        .restaurantName(r.getRestaurantName()).build()).collect(Collectors.toList());
        return  restaurantList;
    }
}
