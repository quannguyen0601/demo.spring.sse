package com.quan.demo.webflux.sse;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *  Handling Request Mapping of Movie.
 *  @author QuanNguyen
 *  @version 1.0
 */
@RestController
@RequestMapping("/api")
public class MovieController {


    private final MovieProcessor movieProcessor;

    public MovieController(MovieProcessor movieProcessor) {
        this.movieProcessor = movieProcessor;
    }

    /**
     * Input movie to the sink.
     * @param movie request
     * @return Void.
     */
    @PostMapping("/movie")
	Mono<Void> create(@RequestBody final Movie movie) {
        return Mono.just(movie).map(i -> {
            if (i.getId() % 2 == 0) {
                i.setEvent("EVEN");
            } else {
                i.setEvent("ODD");
            }
            movieProcessor.notify(movie);
            return i;
        }).then();
    }

    /**
     * Create the stream Flux.
     * @return
     */
    @GetMapping(path = "/stream-flux",
        produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<Movie>> streamFlux(@RequestParam(value = "isOdd",defaultValue = "true")Boolean isOdd) {
        return movieProcessor.process(isOdd);
    }
}
