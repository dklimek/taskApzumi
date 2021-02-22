package com.klimek.demo.restApi.services;

import com.klimek.demo.restApi.dto.PostQueryDTO;
import com.klimek.demo.restApi.dto.PostRodoDTO;
import com.klimek.demo.restApi.entities.ArchivedPost;
import com.klimek.demo.restApi.entities.Post;
import com.klimek.demo.restApi.exception.PostExistsWithThisIdException;
import com.klimek.demo.restApi.exception.PostNotfoundException;
import com.klimek.demo.restApi.repositories.ArchivedPostRepository;
import com.klimek.demo.restApi.repositories.PostRepository;
import com.klimek.demo.restApi.schedulingtasks.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostQueryServiceImpl implements PostQueryService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ArchivedPostRepository archivedPostRepository;

    PostQueryServiceImpl(PostRepository postRepository, ArchivedPostRepository archivedPostRepository){
        this.postRepository = postRepository;
        this.archivedPostRepository = archivedPostRepository;
    }

    @Override
    public PostQueryDTO getPost(Long id) {
        if (postRepository.findById(id).isPresent()){
            Post fetchedPost = postRepository.findById(id).get();
            return new PostQueryDTO(fetchedPost.getId(),fetchedPost.getUserId(), fetchedPost.getTitle(), fetchedPost.getBody());
        }else{
            throw new PostNotfoundException();
        }
    }

    @Override
    public PostRodoDTO getPostRodo(Long id) {
        if (postRepository.findById(id).isPresent()){
            Post fetchedPost = postRepository.findById(id).get();
            return new PostRodoDTO(fetchedPost.getId(),fetchedPost.getUserId(), fetchedPost.getTitle(), fetchedPost.getBody());
        }else{
            throw new PostNotfoundException();
        }
    }

    @Override
    public List<PostQueryDTO> getAllPostList() {
        List<PostQueryDTO> postList = new ArrayList<>();

        postRepository.findAll().forEach(post -> {
            postList.add(new PostQueryDTO(post.getId(), post.getUserId(), post.getTitle(), post.getBody()));
        });

        return postList;
    }

    @Override
    public List<PostRodoDTO> getAllPostListRodo() {
        List<PostRodoDTO> postList = new ArrayList<>();

        postRepository.findAll().forEach(post -> {
            postList.add(new PostRodoDTO(post.getId(), post.getUserId(), post.getTitle(), post.getBody()));
        });

        return postList;
    }

    @Override
    public List<PostQueryDTO> getAllPostListContainingTittle(String title) {
        List<PostQueryDTO> postList = new ArrayList<>();

        postRepository.findByTitleContaining(title).forEach(post -> {
            postList.add(new PostQueryDTO(post.getId(), post.getUserId(), post.getTitle(), post.getBody()));
        });

        return postList;
    }

    @Override
    public List<PostRodoDTO> getAllPostListRodoContainingTittle(String title) {
        List<PostRodoDTO> postList = new ArrayList<>();

        postRepository.findByTitleContaining(title).forEach(post -> {
            postList.add(new PostRodoDTO(post.getId(), post.getUserId(), post.getTitle(), post.getBody()));
        });

        return postList;
    }

    @Override
    public PostQueryDTO addPost(Long id, Long userId, String title, String body) {

        if(!postRepository.findById(id).isPresent()){
            Post addPost = new Post();
            addPost.setId(id);
            addPost.setUserId(userId);
            addPost.setTitle(title);
            addPost.setBody(body);
            Post addedPost = postRepository.save(addPost);
            return new PostQueryDTO(addedPost.getId(), addedPost.getUserId(), addedPost.getTitle(), addedPost.getBody());
        } else {
            throw new PostExistsWithThisIdException();
        }

    }

    @Override
    public PostQueryDTO addPostWithGeneratedId(Long userId, String title, String body) {
        Post addPost = new Post();
        Post postWithTheMaxId = postRepository.findTopByOrderByIdDesc();
        Long newId = postWithTheMaxId.getId() + 1L;
        addPost.setId(newId);
        addPost.setUserId(userId);
        addPost.setTitle(title);
        addPost.setBody(body);
        Post addedPost = postRepository.save(addPost);
        return new PostQueryDTO(addedPost.getId(), addedPost.getUserId(), addedPost.getTitle(), addedPost.getBody());
    }

    @Override
    public PostQueryDTO deletePost(Long id) {

        if (postRepository.findById(id).isPresent()){
                Post postToDelete = postRepository.findById(id).get();
                savePostToArchive(postToDelete);
                postRepository.deleteById(id);
            return new PostQueryDTO(postToDelete.getId(), postToDelete.getUserId(), postToDelete.getTitle(), postToDelete.getBody());
        }else{
            throw new PostNotfoundException();
        }

    }

    @Override
    public PostQueryDTO updatePost(Long id, String title, String body) {

        if (postRepository.findById(id).isPresent()){
            Post fetchedPost = postRepository.findById(id).get();
            savePostToArchive(fetchedPost);
            fetchedPost.setTitle(title);
            fetchedPost.setBody(body);
            postRepository.save(fetchedPost);
            return new PostQueryDTO(fetchedPost.getId(), fetchedPost.getUserId(), fetchedPost.getTitle(), fetchedPost.getBody());
        }else{
            throw new PostNotfoundException();
        }
    }

    public void savePostToArchive(Post postToArchive){
        ArchivedPost archPost = new ArchivedPost(postToArchive.getId(),postToArchive.getUserId(), postToArchive.getTitle(), postToArchive.getBody());
        archivedPostRepository.save(archPost);
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "CET")
    public void updateDatabase() {
        new ScheduledTasks().updateDatabase(postRepository, archivedPostRepository);
    }
}
