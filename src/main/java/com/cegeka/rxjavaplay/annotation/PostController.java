package com.cegeka.rxjavaplay.annotation;

import com.cegeka.rxjavaplay.annotation.dao.Joke;
import com.cegeka.rxjavaplay.annotation.dao.Post;
import com.cegeka.rxjavaplay.annotation.dao.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = "/posts")
class PostController {

    private final PostRepository postRepository;

    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private WebClient webClient;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> all() {
        return this.postRepository
                .findAll()
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable("id") String id) {
        return this.postRepository.findById(id);
    }

    @PostMapping
    public Mono<Post> create(@RequestBody Post post) {
        return this.postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") String id, @RequestBody Post post) {
        return this.postRepository.findById(id)
                .map(p -> {
                    p.setTitle(post.getTitle());
                    p.setContent(post.getContent());

                    return p;
                })
                .flatMap(this.postRepository::save);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.postRepository.deleteById(id);
    }

    @GetMapping(value = "/jokes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> getJokes() {
        return Flux.interval(Duration.ofSeconds(3))
                .flatMap(this::getJoke);

    }

    private Mono<Post> getJoke(long id) {
        return webClient
                .get()
                .uri("https://sv443.net/jokeapi/category/Programming")
//                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToMono(Joke.class)
                .filter(joke -> joke.getJoke() != null)
                .map(joke1 -> getPost(id, joke1));
    }

    private Post getPost(long id, Joke joke) {
        logger.info(joke.getJoke());
        return new Post(String.valueOf(id), joke.getJoke());
    }
}
