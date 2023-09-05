package com.sky.ombdservice.controller;

import com.sky.ombdservice.models.ApiResponse;
import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.service.ApiResponseService;
import com.sky.ombdservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ApiResponseService apiResponseService;

    @Autowired
    public MovieController(MovieService movieService, ApiResponseService apiResponseService) {
        this.movieService = movieService;
        this.apiResponseService = apiResponseService;
    }

    /**
     * Retrieve a list of all movies.
     * <p>
     * This endpoint fetches a list of all movies and returns a response with the movie details.
     * If movies are found, a successful response with the list of movies is returned. If no movies
     * are found, a "Not Found" response is returned with an appropriate error message.
     *
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the list of movies or an error message.
     * - If movies are found, a "200 OK" response with the list of movies is returned.
     * - If no movies are found, a "404 Not Found" response with an error message is returned.
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Movie>>> getAllMovies() {
        Optional<List<Movie>> moviesOptional = movieService.getAllMovies();

        if (moviesOptional.isPresent()) {
            List<Movie> movies = moviesOptional.get();
            return apiResponseService.buildResponse(true, "Movies found", movies);
        } else {
            return apiResponseService.buildErrorResponse(false, "No movies found");
        }
    }

    /**
     * Retrieve a movie by its unique identifier.
     * <p>
     * This endpoint fetches a movie by its unique identifier (ID) and returns a response with the
     * movie details. If a movie with the specified ID is found, a successful response with the movie
     * details is returned. If no movie is found for the given ID, a "Not Found" response is returned.
     *
     * @param id The unique identifier of the movie to retrieve.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the movie details or an error message.
     * - If the movie is found, a "200 OK" response with the movie details is returned.
     * - If the movie is not found, a "404 Not Found" response with an error message is returned.
     */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Movie>> getMovieById(@PathVariable Long id) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            return apiResponseService.buildResponse(true, "Movie found", movie);
        } else {
            return apiResponseService.buildErrorResponse(false, "Movie not found for id: " + id);
        }
    }

    /**
     * Check if a movie has won the "Best Picture" Oscar.
     * <p>
     * This endpoint checks whether a movie with the specified unique identifier (ID) has won the
     * "Best Picture" Oscar. It returns a response indicating the "Best Picture" status of the movie.
     * If the movie is found and has won the "Best Picture" Oscar, a successful response is returned
     * with the status "true." If the movie is not found or has not won the award, a "Not Found" response
     * is returned with an appropriate error message.
     *
     * @param id The unique identifier of the movie to check.
     * @return A {@link ResponseEntity} containing an {@link ApiResponse} with the "Best Picture" status
     * of the movie or an error message.
     * - If the movie is found and has won the award, a "200 OK" response with the status "true" is returned.
     * - If the movie is not found or has not won the award, a "404 Not Found" response with an error message is returned.
     */

    @GetMapping("/{id}/best-picture")
    public ResponseEntity<ApiResponse<Boolean>> hasWonBestPicture(@PathVariable Long id) {
        Optional<Boolean> bestPictureOptional = movieService.hasWonBestPicture(id);

        if (bestPictureOptional.isPresent()) {
            Boolean hasWon = bestPictureOptional.get();
            return apiResponseService.buildResponse(true, "Best Picture status retrieved", hasWon);
        } else {
            return apiResponseService.buildErrorResponse(false, "Movie not found for id: " + id);
        }
    }
}
