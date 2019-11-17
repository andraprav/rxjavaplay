package com.cegeka.rxjavaplay;

import com.cegeka.rxjavaplay.annotation.dao.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(scanBasePackages = { "com.cegeka.rxjavaplay" })
public class TestWebClient {
    Logger logger = LoggerFactory.getLogger(TestWebClient.class);
    @Bean
    WebClient getWebClient() {
        return WebClient.create("http://localhost:8080");
    }
    @Bean
    CommandLineRunner demo(WebClient client) {
        return args -> {
            client.get()
                    .uri("/")
                    .accept(MediaType.TEXT_EVENT_STREAM)
                    .retrieve()
//                    .bodyToFlux(Integer.class)
                    .bodyToFlux(Employee.class)
                    .map(String::valueOf)
                    .subscribe(msg -> {
                        logger.info(msg);
                    });
        };
    }
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestWebClient.class).properties(java.util.Collections.singletonMap("server.port", "8080"))
                .run(args);
    }
}
