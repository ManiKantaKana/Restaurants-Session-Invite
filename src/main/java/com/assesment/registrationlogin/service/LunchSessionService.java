package com.assesment.registrationlogin.service;

import com.assesment.registrationlogin.model.LunchSession;
import com.assesment.registrationlogin.web.dto.LunchSessionDto;

import java.util.List;

public interface LunchSessionService {
    LunchSession saveLunchSession(LunchSessionDto lunchSessionDto);
    public List<LunchSessionDto> listAllSession(String userName);
    void endSession(Long id);

    Object listAllSessionInv(String loggedInUserName);
}
