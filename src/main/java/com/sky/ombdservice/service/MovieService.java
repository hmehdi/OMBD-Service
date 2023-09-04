package com.sky.ombdservice.service;

import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.repository.MovieRepository;
import com.sky.ombdservice.repository.OscarWinnerRepository;
import com.sky.ombdservice.utility.UrlGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;


@Service
@AllArgsConstructor
public class MovieService {

    private final UrlGenerator urlGenerator;
    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;
    private final OscarWinnerRepository oscarWinnerRepository;

    public ResponseEntity<Movie> find(final String movieTitle) {
        var url = urlGenerator.generate(movieTitle);
        final var response = restTemplate.getForEntity(urlGenerator.generate(movieTitle), Movie.class);
        final var movieDto = response.getBody();

        if (movieDto.getResponse().equalsIgnoreCase("FALSE"))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(movieDto);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        return movieOptional.orElse(null);
    }

    public boolean hasWonBestPicture(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            List<OscarWinner> oscarWinners = oscarWinnerRepository.findByNameAndOscarYear(
                    movie.getTitle(),
                    movie.getYear()
            );

            // Check if any entry in oscarWinners has won the Best Picture Oscar
            for (OscarWinner oscarWinner : oscarWinners) {
                if (oscarWinner.isBestPictureWinner()) {
                    return true;
                }
            }
        }

        return false;
    }
}
