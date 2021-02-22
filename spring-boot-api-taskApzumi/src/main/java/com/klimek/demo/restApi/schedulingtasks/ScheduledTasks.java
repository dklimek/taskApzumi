package com.klimek.demo.restApi.schedulingtasks;

import com.klimek.demo.restApi.entities.Post;
import com.klimek.demo.restApi.repositories.ArchivedPostRepository;
import com.klimek.demo.restApi.repositories.PostRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {

    public void updateDatabase(PostRepository postRepository, ArchivedPostRepository archivedPostRepository) {
        String BASE_URL = "https://jsonplaceholder.typicode.com/posts";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Post>> postResponse =
                restTemplate.exchange(BASE_URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {
                        });
        List<Post> posts = postResponse.getBody();
        List<BigInteger> archivedPostsIdList = archivedPostRepository.findAllUniqueUserId();
        List<Long> archivedPostsIdListAsLong = archivedPostsIdList.stream().map(i -> i.longValue()).collect(Collectors.toList());

        List<Post> operatedList = new ArrayList<>();
        posts.stream()
                .filter(item -> archivedPostsIdListAsLong.contains(item.getId()))
                .forEach(item -> {
                    operatedList.add(item);
                });
        posts.removeAll(operatedList);

        postRepository.saveAll(posts);
    }
}
