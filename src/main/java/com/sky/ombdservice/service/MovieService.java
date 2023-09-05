package com.sky.ombdservice.service;

import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.repository.MovieRepository;
import com.sky.ombdservice.repository.OscarWinnerRepository;
import com.sky.ombdservice.utility.UrlGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


@Service
public record MovieService(UrlGenerator urlGenerator,
                           RestTemplate restTemplate,
                           MovieRepository movieRepository,
                           OscarWinnerRepository oscarWinnerRepository) {

    public ResponseEntity<Movie> find(final String movieTitle) {
        var url = urlGenerator.generate(movieTitle);
        final var response = restTemplate.getForEntity(urlGenerator.generate(movieTitle), Movie.class);
        final var movieDto = response.getBody();

        if (movieDto.getResponse().equalsIgnoreCase("FALSE"))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(movieDto);
    }
    public Optional<List<Movie>> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return Optional.ofNullable(movies);
    }
//    public List<Movie> getAllMovies() {
//        return movieRepository.findAll();
//    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Boolean> hasWonBestPicture(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        return movieOptional.map(movie -> {
            List<OscarWinner> oscarWinners = oscarWinnerRepository.findByNameAndOscarYear(
                    movie.getTitle(),
                    movie.getYear()
            );

            return oscarWinners.stream()
                    .anyMatch(OscarWinner::isBestPictureWinner);
        });
    }
}
