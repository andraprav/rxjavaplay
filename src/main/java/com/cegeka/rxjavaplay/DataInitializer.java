package com.cegeka.rxjavaplay;

import com.cegeka.rxjavaplay.annotation.dao.Post;
import com.cegeka.rxjavaplay.annotation.dao.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
class DataInitializer {
    //implements CommandLineRunner {

    Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final PostRepository posts;

    public DataInitializer(PostRepository posts) {
        this.posts = posts;
    }

    // @Override
    public void run(String[] args) {
        log.info("start data initialization  ...");
        this.posts
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> this.posts.save(new Post(title, "content of " + title))
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization...")
                );

    }

}
