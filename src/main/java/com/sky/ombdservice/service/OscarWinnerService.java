package com.sky.ombdservice.service;

import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.repository.OscarWinnerRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record OscarWinnerService(OscarWinnerRepository oscarWinnerRepository) {

    public Optional<List<OscarWinner>> getAllOscarWinners() {
        List<OscarWinner> oscarWinners =  oscarWinnerRepository.findAll();
        return Optional.ofNullable(oscarWinners);
    }


    public Optional<OscarWinner> getOscarWinnerById(Long id) {
        return oscarWinnerRepository.findById(id);
    }

    public Optional<List<OscarWinner>> getOscarWinnersByMovie(String movieName) {
        List<OscarWinner> oscarWinners = oscarWinnerRepository.findByMovie(movieName);
        return Optional.ofNullable(oscarWinners);
    }

    public Optional<List<OscarWinner>> getOscarWinnersByAward(String awardCategory) {
        List<OscarWinner> oscarWinners = oscarWinnerRepository.findByAward(awardCategory);
        return Optional.ofNullable(oscarWinners);
    }


    public Optional<OscarWinner> addOscarWinner(OscarWinner oscarWinner) {
        if (isOscarWinnerDataValid(oscarWinner)) {
            OscarWinner addedOscarWinner = oscarWinnerRepository.save(oscarWinner);
            return Optional.of(addedOscarWinner);
        } else {
            return Optional.empty();
        }
    }

    private boolean isOscarWinnerDataValid(OscarWinner oscarWinner) {
        return !StringUtils.isBlank(oscarWinner.getName())
                && !StringUtils.isBlank(oscarWinner.getAward())
                && oscarWinner.getOscarYear() != null;
    }

    public Optional<OscarWinner> updateOscarWinner(Long id, OscarWinner updatedOscarWinner) {
        return oscarWinnerRepository.findById(id)
                .map(existingOscarWinner -> {
                    existingOscarWinner.setName(updatedOscarWinner.getName());
                    existingOscarWinner.setOscarYear(updatedOscarWinner.getOscarYear());
                    existingOscarWinner.setAward(updatedOscarWinner.getAward());
                    return oscarWinnerRepository.save(existingOscarWinner);
                });
    }

    public Optional<Boolean> deleteOscarWinnerById(Long id) {
        Optional<OscarWinner> existingOscarWinner = oscarWinnerRepository.findById(id);

        if (existingOscarWinner.isPresent()) {
            oscarWinnerRepository.deleteById(id);
            return Optional.of(true); // Deletion successful
        } else {
            return Optional.empty();
        }
    }

    public Optional<Boolean> isBestPictureWinner(String movie, String oscarYear) {
        List<OscarWinner> bestPictureWinners = oscarWinnerRepository.findByMovieAndOscarYearAndAward(movie, oscarYear, "Best Picture");
        return Optional.of(!bestPictureWinners.isEmpty());
    }

}

