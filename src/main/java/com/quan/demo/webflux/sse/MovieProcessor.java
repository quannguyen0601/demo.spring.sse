package com.quan.demo.webflux.sse;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import java.time.Duration;
import java.util.UUID;

@Service
public class MovieProcessor {
  private final FluxProcessor<Movie, Movie> processor;
  private final FluxSink<Movie> sink;
  private  static final Long RETRY_IN_SECONDS = 5L;

  public MovieProcessor() {
      processor = EmitterProcessor.<Movie>create().serialize();
      sink = processor.sink();
  }

  public void notify(Movie movie) {
    sink.next(movie);
  }

  public Flux<ServerSentEvent<Movie>> process(Boolean iBoolean) {
    return processor
      .filter(c -> {
        if (iBoolean) {
          return c.getEvent().equals("ODD");
        } else {
          return c.getEvent().equals("EVEN");
        }
      })
        .map(c -> ServerSentEvent
              .builder(c)
              .id(UUID.randomUUID().toString())
              .event(c.getEvent().toLowerCase()+"-event")
              .retry(Duration.ofSeconds(RETRY_IN_SECONDS))
              .build());
  }
  
}