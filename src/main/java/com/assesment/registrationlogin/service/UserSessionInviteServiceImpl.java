package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.LunchSession;
import com.assesment.registrationlogin.model.User;
import com.assesment.registrationlogin.model.UserSessionInvite;
import com.assesment.registrationlogin.repository.LunchSessionRepository;
import com.assesment.registrationlogin.repository.UserSessionInviteRepository;
import com.assesment.registrationlogin.web.dto.LunchSessionDto;
import com.assesment.registrationlogin.web.dto.RestaurantDto;
import com.assesment.registrationlogin.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service("userSessionInviteService")
public class UserSessionInviteServiceImpl implements UserSessionInviteService {

    @Autowired
    private UserSessionInviteRepository userSessionInviteRepository;

    private UserService userService;

    private LunchSessionService lunchSessionService;

    private RestaurantService restaurantService;

    public UserSessionInviteServiceImpl(UserService userService, LunchSessionService lunchSessionService, RestaurantService restaurantService) {
        this.userService = userService;
        this.lunchSessionService = lunchSessionService;
        this.restaurantService = restaurantService;
    }

    @Override
    public List<UserSessionInvite> findAllBySessionId(Long sessionId) {
        return userSessionInviteRepository.findAllBySessionId(sessionId);
    }

    @Override
    public List<UserDto> listInviteUsers(String userName, Long sessionId) {
        List<User> userList = userService.findAllUsers();
        List<UserSessionInvite> userSessionInviteList = userSessionInviteRepository.findAllBySessionId(sessionId);
        List<Long> userInvitedList = userSessionInviteList.stream().map(userSessionInvite -> userSessionInvite.getUser().getId()).collect(Collectors.toList());

        List<UserDto> userDtoList =  userList.stream().filter(user -> !userName.equals(user.getEmail()))
                .map(user -> UserDto.builder()
                        .userId(user.getId())
                        .email(user.getEmail())
                        .invited(userInvitedList.contains(user.getId()) ? "Yes" : "No").build()).toList();
        return userDtoList;
    }

    @Override
    public void saveinviteUser(Long inviteUserId, Long sessionId) {
        UserSessionInvite userSessionInvite = new UserSessionInvite();
        userSessionInvite.setUser(new User(inviteUserId));
        userSessionInvite.setLunchSession(new LunchSession(sessionId));
        userSessionInviteRepository.save(userSessionInvite);
    }

    @Override
    public List<UserDto> listAllInvitedUsersBySession(String userName, Long sessionId) {
        List<User> userList = userService.findAllUsers();
        List<UserSessionInvite> userSessionInviteList = userSessionInviteRepository.findAllBySessionId(sessionId);
        List<UserDto> userDtoList =  userSessionInviteList.stream()
                        .map(usi -> UserDto.builder()
                        .userSessionInviteId(usi.getId())
                        .userId(usi.getUser().getId())
                        .email(usi.getUser().getEmail())
                        .invited(userName.equals(usi.getUser().getEmail()) ? "Yes":"No")
                        .invitationAccepted(usi.isUserAcceptInvitation() == false ? "No" : "Yes")
                        .restaurantId(usi.getRestaurant() != null ? usi.getRestaurant().getId():0)
                        .restaurantName(usi.getRestaurant() != null ? usi.getRestaurant().getRestaurantName():"")
                        .build()).toList();
        return userDtoList;
    }

    @Override
    public void acceptInvitation(Long userSessionInviteId) {
        userSessionInviteRepository.acceptInvitation(userSessionInviteId);
    }

    @Override
    public void updateRestaurant(Long userSessionInviteId, Long restaurantId) {
        userSessionInviteRepository.updateRestaurant(userSessionInviteId, restaurantId);
    }

    @Override
    public void randomRestaurantSelection(Long sessionId) {
        List<UserSessionInvite> userSessionInviteList = userSessionInviteRepository.findAllBySessionId(sessionId);
        List<Long> selectedrestaurantList = userSessionInviteList.stream().filter(usi -> usi.getRestaurant() != null).map(usi -> usi.getRestaurant().getId()).collect(Collectors.toList());
        List<RestaurantDto> restaurantDtoList = restaurantService.listAllRestaurants();
        Long restaurantId = null;
        Random r = new Random();
        if(selectedrestaurantList != null && selectedrestaurantList.size() > 0) {
            restaurantId = selectedrestaurantList.get(r.nextInt(selectedrestaurantList.size()));
        } else if(restaurantDtoList != null && restaurantDtoList.size() >0) {
            restaurantId = restaurantDtoList.get(r.nextInt(restaurantDtoList.size())).getId();
        }
        if(restaurantId != null) {
            userSessionInviteRepository.randomRestaurantSelection(sessionId, restaurantId);
        }
    }
}
