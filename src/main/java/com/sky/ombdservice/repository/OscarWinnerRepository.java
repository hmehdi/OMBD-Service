package com.sky.ombdservice.repository;

import com.sky.ombdservice.models.OscarWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OscarWinnerRepository extends JpaRepository<OscarWinner, Long> {


    // Method to retrieve all Oscar winners
    List<OscarWinner> findAll();

    // Method to retrieve an Oscar winner by ID
    Optional<OscarWinner> findById(Long id);


    // Method to retrieve Oscar winners by movie name
    List<OscarWinner> findByMovie(String name);


    // Method to retrieve Oscar winners by award
    List<OscarWinner> findByAward(String award);

    List<OscarWinner> findByNameAndOscarYear(String name, String oscarYear);


    List<OscarWinner> findByAgeGreaterThan(int age);

    List<OscarWinner> findByMovieAndOscarYearAndAward(String movie, String oscarYear, String award);


    // @Query("SELECT o FROM OscarWinner o WHERE o.award = 'Best Picture' AND o.movie = :movie")
    //List<OscarWinner> findBestPictureWinnersForMovie(@Param("movie") String movie);

    // boolean existsByTitleAndOscarYearAndAward(String name, int oscarYear, String award);


}

