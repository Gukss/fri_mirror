package com.project.fri.room.dto;

import com.project.fri.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    public FindRoomResponse(Room room, List<FindAllUserByRoomIdDto> major, List<FindAllUserByRoomIdDto> nonMajor) {
        roomId = room.getId();
        title = room.getTitle();
        location = room.getLocation();
        roomCategory = String.valueOf(room.getRoomCategory());
        headCount = room.getHeadCount();
        isConfirmed = room.isConfirmed();
    }

}

/*
	"roomId": 1,
	"title": "작교 가실 분 :)",
	"location": "학하동",
	"headcount": 4,
	"isConfirmed": false,
	"isParticipate": false, //참여 안하고 있을 때 false
	"major": [
			{
				"name": "제임스",
				"url": "http://ssssss.sss"
			},
			{
				"name": "이금만",
				"url": "http://ssssss.ddd"
			}
	],
	"nonMajor": [ //사람이 없으면 빈 배열 반환

	]
 */