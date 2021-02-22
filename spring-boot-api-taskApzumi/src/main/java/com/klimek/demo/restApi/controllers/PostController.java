package com.klimek.demo.restApi.controllers;

import com.klimek.demo.restApi.dto.PostRodoDTO;
import com.klimek.demo.restApi.entities.PostConstants;
import com.klimek.demo.restApi.services.PostQueryService;
import com.klimek.demo.restApi.dto.PostQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/post")
public class PostController {

    @Autowired
    private PostQueryService postQueryService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostQueryDTO> getPost(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(postQueryService.getPost(id), HttpStatus.OK);
    }

    @GetMapping(value = "/rodo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostRodoDTO> getPostRodo(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(postQueryService.getPostRodo(id), HttpStatus.OK);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostQueryDTO>> getAllPostList() {
        return new ResponseEntity<>(postQueryService.getAllPostList(), HttpStatus.OK);
    }

    @GetMapping(value = "/rodo")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostRodoDTO>> getAllPostListRodo() {
        return new ResponseEntity<>(postQueryService.getAllPostListRodo(), HttpStatus.OK);
    }

    @GetMapping(value = "/ContainingTittle/{title}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostQueryDTO>> findByIdContainingTittle(@PathVariable(value = "title") String title) {
        return new ResponseEntity<>(postQueryService.getAllPostListContainingTittle(title), HttpStatus.OK);
    }

    @GetMapping(value = "/rodo/ContainingTittle/{title}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PostRodoDTO>> findByIdContainingTittleRodo(@PathVariable(value = "title") String title) {
        return new ResponseEntity<>(postQueryService.getAllPostListRodoContainingTittle(title), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostQueryDTO> deletePost(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(postQueryService.deletePost(id), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostQueryDTO> addPost(@RequestBody String payload) {
        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(payload);
            JSONObject jsonObject = (JSONObject) object;
            Long id = (Long) jsonObject.get(PostConstants.id.toString());
            Long userId = (Long) jsonObject.get(PostConstants.userId.toString());
            String title = (String) jsonObject.get(PostConstants.title.toString());
            String body = (String) jsonObject.get(PostConstants.body.toString());
            if (id != null) {
                return new ResponseEntity<>(postQueryService.addPost(id, userId, title, body), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(postQueryService.addPostWithGeneratedId(userId, title, body), HttpStatus.CREATED);
            }
        } catch (ParseException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostQueryDTO> updateTutorial(@PathVariable("id") String id, @RequestBody String payload) {

        try {
            JSONParser parser = new JSONParser();
            Object object = parser.parse(payload);
            JSONObject jsonObject = (JSONObject) object;
            String title = (String) jsonObject.get(PostConstants.title.toString());
            String body = (String) jsonObject.get(PostConstants.body.toString());

            return new ResponseEntity<>(postQueryService.updatePost(Long.valueOf(id), title, body), HttpStatus.OK);

        }catch (ParseException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/downloaddatatodatabase")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostQueryDTO> updateDataInDatabase() throws IOException {
        postQueryService.updateDatabase();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
