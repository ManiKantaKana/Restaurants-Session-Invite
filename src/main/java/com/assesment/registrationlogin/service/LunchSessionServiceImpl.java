package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.LunchSession;
import com.assesment.registrationlogin.model.User;
import com.assesment.registrationlogin.repository.LunchSessionRepository;
import com.assesment.registrationlogin.web.dto.LunchSessionDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LunchSessionServiceImpl implements LunchSessionService {

    @Autowired
    private LunchSessionRepository lunchSessionRepository;

    private UserService userService;

    public LunchSessionServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public LunchSession saveLunchSession(LunchSessionDto lunchSessionDto) {
        LunchSession lunchSession = new LunchSession();
        lunchSession.setSessionName(lunchSessionDto.getSessionName());
        lunchSession.setUser(userService.findByEmail(lunchSessionDto.getUserName()));
        lunchSessionRepository.save(lunchSession);
        return lunchSession;
    }

    @Override
    public List<LunchSessionDto> listAllSession(String userName) {
        User user = userService.findByEmail(userName);
        List<LunchSession> lunchSessions = lunchSessionRepository.findAllByUser(user.getId());
        List<LunchSessionDto> lunchSessionDtoList = lunchSessions.stream().map(lunchSession -> {
            LunchSessionDto lunchSessionDto = new LunchSessionDto();
            lunchSessionDto.setId(lunchSession.getId());
            lunchSessionDto.setUserId(lunchSession.getUser().getId());
            lunchSessionDto.setSessionName(lunchSession.getSessionName());
            lunchSessionDto.setEnded(lunchSession.isEnded() ? "Yes" : "No");
            return lunchSessionDto;
        }).collect(Collectors.toList());

        return lunchSessionDtoList;
    }

    @Override
    public void endSession(Long id) {
        lunchSessionRepository.endSession(id);
    }

    @Override
    public Object listAllSessionInv(String userName) {
        //User user = userService.findByEmail(userName);
        List<LunchSession> lunchSessions = lunchSessionRepository.findAll();
        List<LunchSessionDto> lunchSessionDtoList = lunchSessions.stream().map(lunchSession -> {
            LunchSessionDto lunchSessionDto = new LunchSessionDto();
            lunchSessionDto.setId(lunchSession.getId());
            lunchSessionDto.setUserId(lunchSession.getUser().getId());
            lunchSessionDto.setSessionName(lunchSession.getSessionName());
            lunchSessionDto.setEnded(lunchSession.isEnded() ? "Yes" : "No");
            return lunchSessionDto;
        }).collect(Collectors.toList());

        return lunchSessionDtoList;
    }
}
