package com.cegeka.rxjavaplay.annotation;

import com.cegeka.rxjavaplay.annotation.dao.Joke;
import com.cegeka.rxjavaplay.annotation.dao.Post;
import com.cegeka.rxjavaplay.annotation.dao.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = "/posts")
class PostController {

    private final PostRepository posts;

    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private RestTemplate restTemplate;

    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> all() {
        return this.posts
                .findAll()
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable("id") String id) {
        return this.posts.findById(id);
    }

    @PostMapping
    public Mono<Post> create(@RequestBody Post post) {
        return this.posts.save(post);
    }

    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") String id, @RequestBody Post post) {
        return this.posts.findById(id)
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());

                    return p;
                })
                .flatMap(this.posts::save);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.posts.deleteById(id);
    }

    @GetMapping(value = "/jokes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> getJokes() {
        return Flux.fromStream(
                Stream.generate(this::getJoke)
                        .peek(post -> logger.info(post.getContent())))
                .delayElements(Duration.ofSeconds(1));
    }

    private Post getJoke() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(e.getStackTrace());
        }
        String joke = null;
        while (joke == null) {
            joke = restTemplate.getForObject(
                    "https://sv443.net/jokeapi/category/Programming",
                    Joke.class).getJoke();
        }
        return new Post("", joke);
    }
}
