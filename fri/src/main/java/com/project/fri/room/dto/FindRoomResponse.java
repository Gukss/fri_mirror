package com.project.fri.room.dto;

import com.project.fri.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class FindRoomResponse {

    private Long roomId;
    private String title;
    private String location;
    private String roomCategory;
    private int headCount;
    private Boolean isConfirmed;
    private Boolean isParticipate;
    private List<FindAllUserByRoomIdDto> major;
    private List<FindAllUserByRoomIdDto> nonMajor;

    public FindRoomResponse(Room room, Boolean isParticipated, List<FindAllUserByRoomIdDto> majorList, List<FindAllUserByRoomIdDto> nonMajorList) {
        roomId = room.getId();
        title = room.getTitle();
        location = room.getLocation();
        roomCategory = String.valueOf(room.getRoomCategory().getCategory());
        headCount = room.getHeadCount();
        isConfirmed = room.isConfirmed();
        isParticipate = isParticipated;
        major = majorList;
        nonMajor = nonMajorList;
    }

}