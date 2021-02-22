package com.klimek.demo.restApi;

import static org.assertj.core.api.Assertions.assertThat;

import com.klimek.demo.restApi.controllers.PostController;
import com.klimek.demo.restApi.repositories.ArchivedPostRepository;
import com.klimek.demo.restApi.repositories.PostRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private PostController controller;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ArchivedPostRepository archivedPostRepository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
        assertThat(archivedPostRepository).isNotNull();
        assertThat(postRepository).isNotNull();
    }
}
