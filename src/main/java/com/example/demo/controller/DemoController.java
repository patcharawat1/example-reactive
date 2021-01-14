package com.example.demo.controller;

import com.example.demo.model.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/test")
public class DemoController {

    @GetMapping("/{ids}")
    public Mono<String> getSomeOne(@PathVariable String ids) {
            log.info("test");
            Mono.just("A")
                .doFirst(() -> log.info("doFirst..."))
                .doOnRequest(value -> log.info("doOnRequest... {}", value))
                .doOnEach(signal -> log.info("doOnEach... {} : value => {}", signal.getType().name(), signal.get()))
                .doOnNext(value -> log.info("doOnNext... {}", value))
                .doOnCancel(() -> log.info("doOnCacel..."))
                .doOnError(e -> log.info("doOnError... {}", e.getMessage()))
                .doOnSuccess(value -> log.info("doOnSuccess... {}", value))
                .doOnSuccessOrError((value, e) -> log.info("doOnSuccessOrError... {} or {}", value, (e == null ? null : e.getMessage())))
                .doAfterSuccessOrError((value, e) -> log.info("doAfterSuccessOrError... {} or {}", value, (e == null ? null : e.getMessage())))
                .doAfterTerminate(() -> log.info("doAfterTerminate..."))
                .doOnTerminate(() -> log.info("doOnTerminate..."))
                .doOnSubscribe(subscription -> {
                    long id = 1234567890;
                    subscription.request(id);
                    log.debug("doOnSubscribe... {}", id);
                })
                .doFinally(signalType -> log.info("doFinally... {}", signalType.toString()))
                .doOnDiscard(Object.class, value -> log.info("doOnDiscard... {}", value))
                .subscribe();
        return Mono.just("test"+ids);
    }

    @GetMapping("/getArray")
    public Flux<String> getSomeObject() {

        return Flux.fromIterable(Arrays.asList("a","b","c"));
    }
    @PostMapping( produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Test> create(){
        Test ts = new Test() ;
        ts.setId("2");
        ts.setName("teest");
        return Mono.just(ts);
    }

    @GetMapping( "/exception")
    public Mono<String> exception() {
        throw new RuntimeException("Error on /");
    }
}
