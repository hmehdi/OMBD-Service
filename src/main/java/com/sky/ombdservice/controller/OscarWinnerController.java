package com.sky.ombdservice.controller;

import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.service.OscarWinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/oscar-winners")
public class OscarWinnerController {

    @Autowired
    private OscarWinnerService oscarWinnerService;

    // Endpoint to retrieve all Oscar winners
    @GetMapping("/all")
    public List<OscarWinner> getAllOscarWinners() {
        return oscarWinnerService.getAllOscarWinners();
    }

    // Endpoint to retrieve Oscar winner by ID
    @GetMapping("/{id}")
    public Optional<OscarWinner> getOscarWinnerById(@PathVariable Long id) {
        return oscarWinnerService.getOscarWinnerById(id);
    }

    // Endpoint to retrieve Oscar winners by movie title
    @GetMapping("/by-movie/{movie}")
    public List<OscarWinner> getOscarWinnersByMovie(@PathVariable String movie) {
        return oscarWinnerService.getOscarWinnersByMovie(movie);
    }

    //  Endpoint to retrieve Oscar winners by award category
    @GetMapping("/by-award/{award}")
    public List<OscarWinner> getOscarWinnersByAward(@PathVariable String award) {
        return oscarWinnerService.getOscarWinnersByAward(award);
    }

    // Endpoint to check if a movie won the "Best Picture" Oscar for a specific year
    @GetMapping("/isbestpicture")
    public boolean isBestPictureWinner(@RequestParam String movie, @RequestParam(value = "oscarYear", required = true) Integer oscarYear) {
        return oscarWinnerService.isBestPictureWinner(movie, oscarYear.toString());
    }

    // Endpoint to add a new Oscar winner
    @PostMapping
    public OscarWinner addOscarWinner(@RequestBody OscarWinner oscarWinner) {
        return oscarWinnerService.addOscarWinner(oscarWinner);
    }

    // Endpoint to update an existing Oscar winner
    @PutMapping("/{id}")
    public OscarWinner updateOscarWinner(
            @PathVariable Long id,
            @RequestBody OscarWinner updatedOscarWinner
    ) {
        return oscarWinnerService.updateOscarWinner(id, updatedOscarWinner);
    }

    // Endpoint to delete an Oscar winner by ID
    @DeleteMapping("/{id}")
    public void deleteOscarWinner(@PathVariable Long id) {
        oscarWinnerService.deleteOscarWinner(id);
    }
}

