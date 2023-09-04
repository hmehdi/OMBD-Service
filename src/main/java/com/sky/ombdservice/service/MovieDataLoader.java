package com.sky.ombdservice.service;

import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.repository.MovieRepository;
import com.sky.ombdservice.repository.OscarWinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Component
public record MovieDataLoader(MovieRepository movieRepository,
                              OscarWinnerRepository oscarWinnerRepository) {

    @Autowired
    public MovieDataLoader {
    }

    public void loadCsvData() {

        try {
            File file = ResourceUtils.getFile("classpath:data/oscar_winners.csv");
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);

            for (CSVRecord csvRecord : csvParser) {
                // Extract data from CSV record and create OscarWinner objects
                //oscar_no,oscar_yr,award,name,movie,age,birth_pl,birth_date,birth_mo,birth_d,birth_y
                String oscarNo = csvRecord.get("oscar_no");
                String oscarYr = csvRecord.get("oscar_yr");
                String award = csvRecord.get("award");
                String name = csvRecord.get("name");
                String movie = csvRecord.get("movie");
                String age = csvRecord.get("age");
                String birth_pl = csvRecord.get("birth_pl");
                String birth_date = csvRecord.get("birth_date");
                String birth_mo = csvRecord.get("birth_mo");
                String birth_d = csvRecord.get("birth_d");
                String birth_y = csvRecord.get("birth_y");

                // Create and save the OscarWinner object
                OscarWinner oscarWinner = new OscarWinner();
                oscarWinner.setOscarNo(Long.parseLong(oscarNo));
                oscarWinner.setOscarYear(oscarYr);
                oscarWinner.setAward(award);
                oscarWinner.setName(name);
                oscarWinner.setMovie(movie);
                oscarWinner.setAge(Integer.parseInt(age));
                oscarWinner.setBirthPlace(birth_pl);
                oscarWinner.setBirthDate(birth_date);
                oscarWinner.setBirthMonth(birth_mo);
                oscarWinner.setBirthDay(birth_d);
                oscarWinner.setBirthYear(birth_y);

                oscarWinnerRepository.save(oscarWinner);


                // Create and save Movie entity (if it doesn't exist)
                Optional<Movie> existingMovie = movieRepository.findByTitleAndYear(oscarWinner.getName(), oscarWinner.getOscarYear());
                if (existingMovie.isEmpty()) {
                    Movie movieObj = new Movie(oscarWinner.getMovie(), oscarWinner.getOscarYear());
                    movieRepository.save(movieObj);
                }

            }
        } catch (IOException e) {
            // Handle exceptions, e.g., log the error or throw a custom exception
            e.printStackTrace();
        }
    }

}

