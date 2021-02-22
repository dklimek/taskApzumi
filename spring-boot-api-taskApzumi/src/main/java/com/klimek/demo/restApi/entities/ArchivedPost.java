package com.klimek.demo.restApi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "archivedpost")
public class ArchivedPost {

    @Id
    @GeneratedValue
    private UUID id;
    private Long postId;
    private Long userId;
    private String title;
    private String body;
    private Date dateOfArchived;

    public ArchivedPost(Long postId, Long userId, String title, String body) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.dateOfArchived = new Date();
    }
}
