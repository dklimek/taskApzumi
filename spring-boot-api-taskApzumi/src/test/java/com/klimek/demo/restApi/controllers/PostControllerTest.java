package com.klimek.demo.restApi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klimek.demo.restApi.entities.Post;
import com.klimek.demo.restApi.repositories.PostRepository;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostRepository repository;

    @SneakyThrows
    @Before
    public void setUp() {
        if(!repository.findById(1L).isPresent()) {
            mvc.perform(MockMvcRequestBuilders
                    .post("/api/post")
                    .content(asJsonString(new Post(1L, 1L, "title_1", "body_1")))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        }
    }


    @Test
    public void should_get_posts_without_userId() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/api/post/rodo")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].body").exists());
    }

    @Test
    public void should_get_posts_without_userId_by_title() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .post("/api/post")
                .content(asJsonString(new Post(2L, 2L, "title_2", "body_2")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());

        mvc.perform(MockMvcRequestBuilders
                .get("/api/post/rodo/ContainingTittle/title_1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].body").exists())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("title_1"));
    }

    @Test
    public void should_delete_post() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .delete("/api/post/delete/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists());
    }

    @Test
    public void should_edit_post() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .put("/api/post/1")
                .content(asJsonString(new Post(1L, 1L, "titleChanged", "bodyChanged")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titleChanged"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("bodyChanged"));
    }

    @Test
    public void should_create_post() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .post("/api/post")
                .content(asJsonString(new Post(99L, 99L, "title_99", "body_99")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").exists())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(99L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(99L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("title_99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("body_99"));;

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}