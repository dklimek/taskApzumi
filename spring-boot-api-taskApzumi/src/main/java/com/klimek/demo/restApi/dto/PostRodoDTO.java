package com.klimek.demo.restApi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PostRodoDTO extends PostQueryDTO {

    private Long id;
    @JsonIgnore
    private Long userId;
    private String title;
    private String body;

}
