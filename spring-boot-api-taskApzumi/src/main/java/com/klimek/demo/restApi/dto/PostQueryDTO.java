package com.klimek.demo.restApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PostQueryDTO {

    private Long id;
    private Long userId;
    private String title;
    private String body;

}
