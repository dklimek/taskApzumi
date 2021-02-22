package com.klimek.demo.restApi.services;

import com.klimek.demo.restApi.dto.PostQueryDTO;
import com.klimek.demo.restApi.dto.PostRodoDTO;

import java.util.List;

public interface PostQueryService {

    public PostQueryDTO getPost(Long id);
    public PostRodoDTO getPostRodo(Long id);
    public List<PostQueryDTO> getAllPostList();
    public List<PostRodoDTO> getAllPostListRodo();
    public List<PostQueryDTO> getAllPostListContainingTittle(String title);
    public List<PostRodoDTO> getAllPostListRodoContainingTittle(String title);
    public PostQueryDTO addPost(Long id, Long userId, String title, String body);
    public PostQueryDTO addPostWithGeneratedId(Long userId, String title, String body);
    public PostQueryDTO deletePost(Long id);
    public PostQueryDTO updatePost(Long id, String title, String body);
    public void updateDatabase();

}

