package com.sky.ombdservice.controller;

import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // Endpoint to retrieve a list of all movies
    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    // Endpoint to retrieve movie information by ID
    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    // Endpoint to check if a movie has won the "Best Picture" Oscar
    @GetMapping("/{id}/best-picture")
    public boolean hasWonBestPicture(@PathVariable Long id) {
        return movieService.hasWonBestPicture(id);
    }

}
