package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.User;
import com.assesment.registrationlogin.web.dto.UserDto;
import com.assesment.registrationlogin.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
    User findByEmail(String email);

    List<User> findAllUsers();
}
