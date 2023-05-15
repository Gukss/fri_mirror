package com.project.fri.scrap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindScrapListResponse {
    private List<FindScrapResponse> scrapList;
}
