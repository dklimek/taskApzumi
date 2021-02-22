package com.klimek.demo.restApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class PostQueryDTO {

    private Long id;
    private Long userId;
    private String title;
    private String body;


}
