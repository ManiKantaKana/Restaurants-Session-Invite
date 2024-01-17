package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.Role;
import com.assesment.registrationlogin.model.User;
import com.assesment.registrationlogin.web.dto.UserDto;
import com.assesment.registrationlogin.web.dto.UserRegistrationDto;
import com.assesment.registrationlogin.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User (registrationDto.getFirstName (), registrationDto.getLastName (),
                registrationDto.getEmail (), passwordEncoder.encode ( registrationDto.getPassword () ),
                Arrays.asList (new Role("ROLE USER")));
        return userRepository.save ( user );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail ( username );
        if(user == null){
            throw new UsernameNotFoundException ( "Invalid username or password." );
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail (),user.getPassword (), mapRolesToAuthorities ( user.getRoles () ));
    }
    @Override
    public User findByEmail(String userName) {
        return userRepository.findByEmail (userName);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream ().map ( role -> new SimpleGrantedAuthority ( role.getName () ) ).collect ( Collectors.toList (  ) );
    }
}
