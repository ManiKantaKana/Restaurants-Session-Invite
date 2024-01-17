package com.assesment.registrationlogin.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LunchSessionDto {

    private Long id;
    private Long userId;
    private String sessionName;
    private String userName;
    private String ended;

}
