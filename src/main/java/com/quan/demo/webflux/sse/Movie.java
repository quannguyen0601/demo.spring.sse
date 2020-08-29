package com.quan.demo.webflux.sse;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Movie.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long id;
    private String Title;
    private String Year;
    private String Rate;
    private Date Released;
    private String Runtime;
    private String Genre;
    private String Plot;
    private String event;
}