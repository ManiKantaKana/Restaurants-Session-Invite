package com.assesment.registrationlogin.web;

import com.assesment.registrationlogin.model.Restaurant;
import com.assesment.registrationlogin.service.LunchSessionService;
import com.assesment.registrationlogin.service.RestaurantService;
import com.assesment.registrationlogin.service.UserService;
import com.assesment.registrationlogin.service.UserSessionInviteService;
import com.assesment.registrationlogin.web.dto.InviteUsersDto;
import com.assesment.registrationlogin.web.dto.LunchSessionDto;
import com.assesment.registrationlogin.web.dto.RestaurantDto;
import com.assesment.registrationlogin.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private LunchSessionService lunchSessionService;
    private UserSessionInviteService userSessionInviteService;

    private RestaurantService restaurantService;

    public MainController(LunchSessionService lunchSessionService, UserSessionInviteService userSessionInviteService, RestaurantService restaurantService) {
        this.lunchSessionService = lunchSessionService;
        this.userSessionInviteService = userSessionInviteService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("/createLunchSession")
    public String createLunchSession(Model model) {

        model.addAttribute("lunchSession", new LunchSessionDto());
        setLunchSessionListModel(model);
        return "lunchSession";
    }

    @PostMapping("/saveLunchSession")
    public String saveLunchSession(@ModelAttribute LunchSessionDto lunchSessionDto, Model model) {
        lunchSessionDto.setUserName(getLoggedInUserName());
        lunchSessionService.saveLunchSession(lunchSessionDto);
        setLunchSessionListModel(model);
        return "lunchSession";
    }

    @GetMapping("/endSession/{id}")
    public String endSession(Model model, @PathVariable String id)
    {
        lunchSessionService.endSession(Long.parseLong(id));
        setLunchSessionListModel(model);
        //Random pick the restaurant for not selected users in the session
        userSessionInviteService.randomRestaurantSelection(Long.parseLong(id));
        return "lunchSession";
    }

    @GetMapping("/inviteUsers")
    public String inviteUsers(Model model)
    {
        Map<Long, String> sessionEndMap = lunchSessionService.listAllSession(getLoggedInUserName())
                .stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getEnded()));
        model.addAttribute("listAllSession", lunchSessionService.listAllSession(getLoggedInUserName()));
        model.addAttribute("inviteUsers", new InviteUsersDto());
        model.addAttribute("inviteUsersList", null);
        model.addAttribute("sessionEndMap", sessionEndMap);

        return "inviteUsers";
    }

    @GetMapping("/saveinviteUser/{inviteUserId}/{sessionId}")
    public String saveinviteUser(Model model, @PathVariable String inviteUserId, @PathVariable String sessionId)
    {
        userSessionInviteService.saveinviteUser(Long.parseLong(inviteUserId), Long.parseLong(sessionId));
        Map<Long, String> sessionEndMap = lunchSessionService.listAllSession(getLoggedInUserName())
                .stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getEnded()));
        model.addAttribute("listAllSession", lunchSessionService.listAllSession(getLoggedInUserName()));
        model.addAttribute("inviteUsers", new InviteUsersDto(sessionId));
        model.addAttribute("inviteUsersList", null);
        model.addAttribute("sessionEndMap", sessionEndMap);
        return "inviteUsers";
    }

    @GetMapping("/getUsersBySession")
    @ResponseBody
    public List<UserDto> getUsersBySession(@RequestParam("sessionId") String sessionId) {
        List<UserDto> inviteUsersList = userSessionInviteService.listInviteUsers(getLoggedInUserName(), Long.parseLong(sessionId));
        return inviteUsersList;
    }

    @GetMapping("/restaurantSelAndInviteAccept")
    public String restaurantSelAndInviteAccept(Model model)
    {
        Map<Long, String> sessionEndMap = lunchSessionService.listAllSession(getLoggedInUserName())
                .stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getEnded()));
        List<RestaurantDto> restaurantList = restaurantService.listAllRestaurants();
        Map<Long, String> restaurantMap = restaurantList.stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getRestaurantName()));
        model.addAttribute("listAllSession", lunchSessionService.listAllSessionInv(getLoggedInUserName()));
        model.addAttribute("invitedUsersList", null);
        model.addAttribute("sessionEndMap", sessionEndMap);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("restaurantMap", restaurantMap);

        return "restaurantSelAndInviteAccept";
    }

    @GetMapping("/getinvitedUsersBySession")
    @ResponseBody
    public List<UserDto> getinvitedUsersBySession(@RequestParam("sessionId") String sessionId) {
        List<UserDto> invitedUsersList = userSessionInviteService.listAllInvitedUsersBySession(getLoggedInUserName(), Long.parseLong(sessionId));
        return invitedUsersList;
    }

    @GetMapping("/acceptInvitation/{id}")
    public String acceptInvitation(Model model, @PathVariable String id)
    {
        userSessionInviteService.acceptInvitation(Long.parseLong(id));
        Map<Long, String> sessionEndMap = lunchSessionService.listAllSession(getLoggedInUserName())
                .stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getEnded()));
        List<RestaurantDto> restaurantList = restaurantService.listAllRestaurants();
        Map<Long, String> restaurantMap = restaurantList.stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getRestaurantName()));
        model.addAttribute("listAllSession", lunchSessionService.listAllSessionInv(getLoggedInUserName()));
        model.addAttribute("inviteUsersList", null);
        model.addAttribute("sessionEndMap", sessionEndMap);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("restaurantMap", restaurantMap);
        return "restaurantSelAndInviteAccept";
    }

    @GetMapping("/updateRestaurant/{id}/{restaurantId}")
    public String updateRestaurant(Model model, @PathVariable String id, @PathVariable String restaurantId)
    {
        userSessionInviteService.updateRestaurant(Long.parseLong(id), Long.parseLong(restaurantId));
        Map<Long, String> sessionEndMap = lunchSessionService.listAllSession(getLoggedInUserName())
                .stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getEnded()));
        List<RestaurantDto> restaurantList = restaurantService.listAllRestaurants();
        Map<Long, String> restaurantMap = restaurantList.stream().collect(Collectors.toMap(lSession->lSession.getId(), lSession->lSession.getRestaurantName()));
        model.addAttribute("listAllSession", lunchSessionService.listAllSessionInv(getLoggedInUserName()));
        model.addAttribute("inviteUsersList", null);
        model.addAttribute("sessionEndMap", sessionEndMap);
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("restaurantMap", restaurantMap);
        return "restaurantSelAndInviteAccept";
    }

    public void setLunchSessionListModel(Model model) {
        model.addAttribute("listAllSession", lunchSessionService.listAllSession(getLoggedInUserName()));
    }

    public String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = null;
        if (authentication != null) {
            // Retrieve the principal, which is the authenticated user details
            Object principal = authentication.getPrincipal();

            // The principal should be an instance of UserDetails if you've used DaoAuthenticationProvider
            if (principal instanceof UserDetails) {
                userDetails = (UserDetails) principal;
            }
        }
        return userDetails.getUsername();
    }

}
