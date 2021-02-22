package com.klimek.demo.restApi.repositories;

import com.klimek.demo.restApi.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "post", path = "post")
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findTopByOrderByIdDesc();
    List<Post> findByTitleContaining(String title);
}
