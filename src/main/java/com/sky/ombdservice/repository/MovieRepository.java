package com.sky.ombdservice.repository;

import com.sky.ombdservice.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Custom query method to find movies by title and release year
    Optional<Movie> findByTitleAndYear(String title, String year);

}
