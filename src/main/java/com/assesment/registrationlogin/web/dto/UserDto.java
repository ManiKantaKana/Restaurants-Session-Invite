package com.assesment.registrationlogin.web.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@Data
public class UserDto {
    private Long userId;
    private Long userSessionInviteId;
    private String email;
    private String invited;
    private String invitationAccepted;
    private Long restaurantId;
    private String restaurantName;
}
