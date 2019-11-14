package com.cegeka.rxjavaplay.annotation.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Document
public class Post implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public Post() {
    }

    public Post(String id, @NotBlank String title, @NotBlank String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Post(@NotBlank String title, @NotBlank String content) {
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public Post setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }
}

