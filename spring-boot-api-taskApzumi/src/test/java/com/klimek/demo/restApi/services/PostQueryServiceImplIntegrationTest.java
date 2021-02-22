package com.klimek.demo.restApi.services;

import com.klimek.demo.restApi.dto.PostQueryDTO;
import com.klimek.demo.restApi.dto.PostRodoDTO;
import com.klimek.demo.restApi.entities.Post;
import com.klimek.demo.restApi.repositories.ArchivedPostRepository;
import com.klimek.demo.restApi.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PostQueryServiceImplIntegrationTest {


    private PostRepository postRepository = Mockito.mock(PostRepository.class);
    private ArchivedPostRepository archivedPostRepository= Mockito.mock(ArchivedPostRepository.class);

    @Test
    void should_find_post_by_id() {
        PostQueryServiceImpl postQueryService = new PostQueryServiceImpl(postRepository,archivedPostRepository);

        Post post = new Post(1L,1L,"1","1");
        PostQueryDTO expectResponse = new PostQueryDTO(1L,1L,"1","1");
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        PostQueryDTO actualPostResponse = postQueryService.getPost(1L);
        Assertions.assertEquals(actualPostResponse,expectResponse);
    }

    @Test
    void should_find_list_of_post_rodo() {

        PostQueryServiceImpl postQueryService = new PostQueryServiceImpl(postRepository,archivedPostRepository);
        List<Post> postList = new ArrayList<Post>();
        postList.add(new Post(1L,1L,"1","1"));
        postList.add(new Post(2L,2L,"2","2"));

        List<PostRodoDTO> expectResponse = new ArrayList<PostRodoDTO>();
        expectResponse.add(new PostRodoDTO(1L,1L,"1","1"));
        expectResponse.add(new PostRodoDTO(2L,2L,"2","2"));

        Mockito.when(postRepository.findAll()).thenReturn((Iterable)postList);
        List<PostRodoDTO> actualPostResponse = postQueryService.getAllPostListRodo();
        Assertions.assertEquals(actualPostResponse,expectResponse);

    }

    @Test
    void should_update_post_by_id() {
        PostQueryServiceImpl postQueryService = new PostQueryServiceImpl(postRepository,archivedPostRepository);

        Post post = new Post(1L,1L,"1","1");
        PostQueryDTO expectResponse = new PostQueryDTO(1L,1L,"titleChanged","bodyChanged");

        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        Mockito.when(postRepository.save(post)).thenReturn(post);

        PostQueryDTO actualPostResponse = postQueryService.updatePost(1L,"titleChanged", "bodyChanged");
        Assertions.assertEquals(actualPostResponse,expectResponse);
    }



}