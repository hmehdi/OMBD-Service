package com.sky.ombdservice.service;

import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.repository.OscarWinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record OscarWinnerService(OscarWinnerRepository oscarWinnerRepository) {

    @Autowired
    public OscarWinnerService {
    }

    // Method to retrieve all Oscar winners
    public List<OscarWinner> getAllOscarWinners() {
        return oscarWinnerRepository.findAll();
    }

    // Method to retrieve an Oscar winner by Id
    public Optional<OscarWinner> getOscarWinnerById(Long id) {
        return oscarWinnerRepository.findById(id);
    }

    // Method to retrieve Oscar winners by movie name
    public List<OscarWinner> getOscarWinnersByMovie(String movieName) {
        return oscarWinnerRepository.findByMovie(movieName);
    }

    // Method to retrieve Oscar winners by award category
    public List<OscarWinner> getOscarWinnersByAward(String awardCategory) {
        return oscarWinnerRepository.findByAward(awardCategory);
    }

    // Method to add a new Oscar winner
    public OscarWinner addOscarWinner(OscarWinner oscarWinner) {
        // Add any necessary validation logic here before saving to the repository
        return oscarWinnerRepository.save(oscarWinner);
    }

    // Method to update an existing Oscar winner
    public OscarWinner updateOscarWinner(Long id, OscarWinner updatedOscarWinner) {
        // Find the existing Oscar winner by ID
        Optional<OscarWinner> existingOscarWinner = oscarWinnerRepository.findById(id);

        if (existingOscarWinner.isPresent()) {
            // Update the properties of the existing Oscar winner
            OscarWinner oscarWinnerToUpdate = existingOscarWinner.get();
            oscarWinnerToUpdate.setName(updatedOscarWinner.getName());
            oscarWinnerToUpdate.setOscarYear(updatedOscarWinner.getOscarYear());
            oscarWinnerToUpdate.setAward(updatedOscarWinner.getAward());
            // Update other properties as needed

            // Save the updated Oscar winner to the repository
            return oscarWinnerRepository.save(oscarWinnerToUpdate);
        } else {
            return null;
        }
    }


    // Method to delete an Oscar winner by ID
    public void deleteOscarWinner(Long id) {
        // Check if the Oscar winner with the given ID exists
        Optional<OscarWinner> existingOscarWinner = oscarWinnerRepository.findById(id);

        if (existingOscarWinner.isPresent()) {
            // If it exists, delete the Oscar winner from the repository
            oscarWinnerRepository.deleteById(id);
        }
    }

    public boolean isBestPictureWinner(String movie, String oscarYear) {
        List<OscarWinner> bestPictureWinners = oscarWinnerRepository.findByMovieAndOscarYearAndAward(movie, oscarYear, "Best Picture");
        return !bestPictureWinners.isEmpty();
    }
}

