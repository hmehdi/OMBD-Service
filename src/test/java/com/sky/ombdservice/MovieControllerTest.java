package com.sky.ombdservice;
import com.sky.ombdservice.controller.MovieController;
import com.sky.ombdservice.controller.dto.ApiResponse;
import com.sky.ombdservice.controller.dto.movie.MovieDto;
import com.sky.ombdservice.controller.dto.movie.MovieMapper;
import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.controller.response.ApiResponseService;
import com.sky.ombdservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MovieControllerTest {

    private MovieController movieController;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private ApiResponseService apiResponseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movieController = new MovieController(movieMapper, movieService, apiResponseService);
    }

    @Test
    public void testGetAllMovies_WhenMoviesExist_ThenReturnListOfMovies() {
        // Given
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1L, "Movie 1", "2022"));
        movies.add(new Movie(2L, "Movie 2", "2023"));

        ApiResponse<List<Movie>> expectedApiResponse = new ApiResponse<>(true, "Movies found", movies);
        ResponseEntity<ApiResponse<List<Movie>>> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);

        // When
        when(movieService.getAllMovies()).thenReturn(Optional.of(movies));
        ResponseEntity<ApiResponse<List<MovieDto>>> responseEntity = movieController.getAllMovies();

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildResponse(true, "Movies found", movies);
    }

    @Test
    public void testGetAllMovies_WhenNoMoviesExist_ThenReturnNotFound() {
        // Given
        when(movieService.getAllMovies()).thenReturn(Optional.empty());

        ApiResponse<List<Movie>> expectedApiResponse = new ApiResponse<>(false, "No movies found",null);
        ResponseEntity<ApiResponse<List<Movie>>> expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(expectedApiResponse);

        // When
        ResponseEntity<ApiResponse<List<MovieDto>>> responseEntity = movieController.getAllMovies();

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildErrorResponse(false, "No movies found");
    }

    @Test
    public void testGetMovieById_WhenMovieExists_ThenReturnMovie() {
        // Given
        Long movieId = 1L;
        Movie movie = new Movie(movieId, "Movie 1", "2022");

        when(movieService.getMovieById(movieId)).thenReturn(Optional.of(movie));

        ApiResponse<Movie> expectedApiResponse = new ApiResponse<>(true, "Movie found", movie);
        ResponseEntity<ApiResponse<Movie>> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);

        // When
        ResponseEntity<ApiResponse<MovieDto>> responseEntity = movieController.getMovieById(movieId);

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildResponse(true, "Movie found", movie);
    }

    @Test
    public void testGetMovieById_WhenMovieDoesNotExist_ThenReturnNotFound() {
        // Given
        Long movieId = 1L;

        when(movieService.getMovieById(movieId)).thenReturn(Optional.empty());

        ApiResponse<Movie> expectedApiResponse = new ApiResponse<>(false, "Movie not found for id: " + movieId,null);
        ResponseEntity<ApiResponse<Movie>> expectedResponseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(expectedApiResponse);

        // When
        ResponseEntity<ApiResponse<MovieDto>> responseEntity = movieController.getMovieById(movieId);

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildErrorResponse(false, "Movie not found for id: " + movieId);
    }
}
