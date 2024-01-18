package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.LunchSession;
import com.assesment.registrationlogin.model.UserSessionInvite;
import com.assesment.registrationlogin.web.dto.LunchSessionDto;
import com.assesment.registrationlogin.web.dto.UserDto;

import java.util.List;

public interface UserSessionInviteService {
    List<UserSessionInvite> findAllBySessionId(Long sessionId);
    List<UserDto> listInviteUsers(String userName, Long sessionId);

    void saveinviteUser(Long inviteUserId, Long sessionId);

    List<UserDto> listAllInvitedUsersBySession(String userName, Long sessionId);

    void acceptInvitation(Long userSessionInviteId);

    void updateRestaurant(Long userSessionInviteId, Long restaurantId);

    void randomRestaurantSelection(Long sessionId);
}
