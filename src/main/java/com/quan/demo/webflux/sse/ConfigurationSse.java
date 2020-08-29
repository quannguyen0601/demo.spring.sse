package com.quan.demo.webflux.sse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

/**
 * Setting For FluxSink of the Movies.
 * ConfigurationSse.java
 */
@Configuration
public class ConfigurationSse {
    @Bean
    FluxSink<Movie> getFluxSink() {
        final FluxProcessor<Movie, Movie> processor = DirectProcessor.<Movie>create().serialize();
        return processor.sink();
    }
}