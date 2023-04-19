package com.project.fri.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FindAllUserByRoomIdDto {
    private String name;
    private String url;

}
