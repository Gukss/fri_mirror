package com.project.fri.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateUserRoomResponse {
    private Long roomId;
    private String title;
    private Boolean isParticipate;
    private Boolean ready;
}
