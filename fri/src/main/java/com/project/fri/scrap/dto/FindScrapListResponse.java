package com.project.fri.scrap.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindScrapListResponse {
    private List<FindScrapResponse> scrapList;
}
