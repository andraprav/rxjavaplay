package com.cegeka.rxjavaplay.annotation.dao;

public class Joke {
    private int id;
    private String joke;

    public int getId() {
        return id;
    }

    public Joke setId(int id) {
        this.id = id;
        return this;
    }

    public String getJoke() {
        return joke;
    }

    public Joke setJoke(String joke) {
        this.joke = joke;
        return this;
    }
}
