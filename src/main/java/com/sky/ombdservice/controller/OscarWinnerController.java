package com.sky.ombdservice.controller;

import com.sky.ombdservice.controller.dto.ApiResponse;
import com.sky.ombdservice.controller.dto.oscarwinner.OscarWinnerDto;
import com.sky.ombdservice.controller.dto.oscarwinner.OscarWinnerMapper;
import com.sky.ombdservice.controller.response.ApiResponseService;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.service.OscarWinnerService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/oscar-winners")
@AllArgsConstructor
public class OscarWinnerController {

    private OscarWinnerService oscarWinnerService;
    private final ApiResponseService apiResponseService;
    private final OscarWinnerMapper oscarWinnerMapper;

    /**
     * Endpoint to retrieve all Oscar winners.
     * <p>
     * This endpoint fetches a list of all Oscar winners and returns a response with the winner details.
     * If Oscar winners are found, a successful response with the list of winners is returned. If no
     * Oscar winners are found, a "Not Found" response is returned with an appropriate error message.
     *
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the list of Oscar winners
     * or an error message.
     * - If Oscar winners are found, a "200 OK" response with the list of winners is returned.
     * - If no Oscar winners are found, a "404 Not Found" response with an error message is returned.
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<OscarWinnerDto>>> getAllOscarWinners() {
        return Optional.ofNullable(oscarWinnerService.getAllOscarWinners())
                .map(oscarWinners -> {
                    List<OscarWinnerDto> oscarWinnerDtos = oscarWinnerMapper.entityToDTO(oscarWinners.get());
                    return apiResponseService.buildResponse(true, "Oscar winners found", oscarWinnerDtos);
                })
                .orElseGet(() -> apiResponseService.buildErrorResponse(false, "No Oscar winners found"));
    }


    /**
     * Endpoint to retrieve an Oscar winner by its unique identifier.
     * <p>
     * This endpoint allows clients to query and retrieve an Oscar winner by its unique identifier (ID).
     * If an Oscar winner with the specified ID is found, a successful response with the winner details
     * is returned. If no winner is found for the given ID, a "Not Found" response is returned.
     *
     * @param id The unique identifier of the Oscar winner to retrieve.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the Oscar winner details
     * or an error message.
     * - If the winner is found, a "200 OK" response with the winner details is returned.
     * - If the winner is not found, a "404 Not Found" response with an error message is returned.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OscarWinnerDto>> getOscarWinnerById(@PathVariable Long id) {
        Optional<OscarWinner> oscarWinnerOptional = oscarWinnerService.getOscarWinnerById(id);

        return oscarWinnerOptional
                .map(oscarWinner -> {
                    OscarWinnerDto oscarWinnerDto = oscarWinnerMapper.entityToDTO(oscarWinner);
                    return apiResponseService.buildResponse(true, "Oscar winner found", oscarWinnerDto);
                })
                .orElseGet(() -> apiResponseService.buildErrorResponse(false, "Oscar winner not found for id: " + id));
    }

    /**
     * Endpoint to retrieve Oscar winners by a specific movie title.
     * <p>
     * This endpoint allows clients to query and retrieve a list of Oscar winners associated with
     * a particular movie title. It takes the movie title as a path parameter and delegates the
     * request to the {@code oscarWinnerService} to retrieve the winners. The result is wrapped in
     * an {@code ApiResponse} and returned as a response.
     *
     * @param movie The title of the movie to filter the Oscar winners. Must not be empty or null.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the list of Oscar winners
     * or an error message.
     * - If Oscar winners are found, a "200 OK" response with the list of winners is returned.
     * - If no Oscar winners are found for the specified movie, a "404 Not Found" response with
     * an error message is returned.
     */
    @GetMapping("/by-movie/{movie}")
    public ResponseEntity<ApiResponse<List<OscarWinner>>> getOscarWinnersByMovie(
            @PathVariable @NotBlank String movie) {

        if (StringUtils.isBlank(movie)) {
            return apiResponseService.buildErrorResponse(false, "Movie title must not be empty or null.");
        }

        return oscarWinnerService.getOscarWinnersByMovie(movie)
                .map(oscarWinners -> apiResponseService.buildResponse(true, "Oscar winners retrieved", oscarWinners))
                .orElseGet(() -> apiResponseService.buildErrorResponse(false, "No Oscar winners found for movie: " + movie));
    }

    /**
     * Endpoint to retrieve Oscar winners by a specific award.
     * <p>
     * This endpoint allows clients to query and retrieve a list of Oscar winners who have received
     * a particular award. It takes the award name as a path parameter and delegates the request to
     * the {@code oscarWinnerService} to retrieve the winners. The result is wrapped in an
     * {@code ApiResponse} and returned as a response.
     *
     * @param award The name of the award to filter the Oscar winners.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the list of Oscar winners
     * or an error message.
     * - If Oscar winners are found, a "200 OK" response with the list of winners is returned.
     * - If no Oscar winners are found for the specified award, a "404 Not Found" response with
     * an error message is returned.
     */
    @GetMapping("/by-award")
    public ResponseEntity<ApiResponse<List<OscarWinner>>> getOscarWinnersByAward(@RequestParam @NotBlank String award) {
        if (StringUtils.isBlank(award)) {
            return apiResponseService.buildErrorResponse(false, "Award name must not be empty or null.");
        }
        return oscarWinnerService.getOscarWinnersByAward(award)
                .map(oscarWinners -> apiResponseService.buildResponse(true, "Oscar winners retrieved", oscarWinners))
                .orElseGet(() -> apiResponseService.buildErrorResponse(false, "No Oscar winners found for award: " + award));
    }

    /**
     * Endpoint to check if a movie won the "Best Picture" Oscar for a specific year.
     * <p>
     * This endpoint allows clients to query whether a movie won the prestigious "Best Picture" Oscar
     * for a particular year. It takes the movie title and the Oscar year as query parameters and
     * delegates the request to the {@code oscarWinnerService} to determine if the movie achieved
     * this honor. The result is wrapped in an {@code ApiResponse} and returned as a response.
     *
     * @param movie     The title of the movie to check for "Best Picture" Oscar status.
     * @param oscarYear The year of the Oscar ceremony to check for "Best Picture" status.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the "Best Picture"
     * status of the movie or an error message.
     * - If the movie has won the award, a "200 OK" response with the status "true" is returned.
     * - If the movie has not won the award or is not found, a "404 Not Found" response with
     * an error message is returned.
     */
    @GetMapping("/isbestpicture")
    public ResponseEntity<ApiResponse<Boolean>> isBestPictureWinner(
            @RequestParam String movie,
            @RequestParam(value = "oscarYear") Integer oscarYear) {

        return oscarWinnerService.isBestPictureWinner(movie, oscarYear.toString())
                .map(hasWon -> apiResponseService.buildResponse(true, "Best Picture status retrieved", hasWon))
                .orElseGet(() -> apiResponseService.buildErrorResponse(false, "Movie not found for movie: " + movie));
    }


    /**
     * Endpoint to add a new Oscar winner.
     * <p>
     * This endpoint allows clients to add a new Oscar winner by providing the Oscar winner details in the request body.
     * If the input data is valid and the Oscar winner is successfully added, a "201 Created" response with the added Oscar
     * winner details is returned. If validation fails, a "400 Bad Request" response with an error message is returned.
     *
     * @param oscarWinnerDto The Oscar winner DTO containing the details to add.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} with the added Oscar winner details if the addition
     * is successful, or a "Bad Request" response with an error message if validation fails.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OscarWinnerDto>> addOscarWinner(@RequestBody OscarWinnerDto oscarWinnerDto) {
        try {
            val oscarWinner = oscarWinnerMapper.dtoToEntity(oscarWinnerDto);
            Optional<OscarWinner> addedWinner = oscarWinnerService.addOscarWinner(oscarWinner);

            return addedWinner
                    .map(winner -> {
                        OscarWinnerDto owDto = oscarWinnerMapper.entityToDTO(winner);
                        return apiResponseService.buildResponse(true, "Oscar winner added successfully", owDto);
                    })
                    .orElseGet(() -> apiResponseService.buildErrorResponse(false, "Invalid Oscar winner data "));
        } catch (ValidationException e) {
            return apiResponseService.buildErrorResponse(false, e.getMessage());
        }
    }


    /**
     * Endpoint to update an existing Oscar winner.
     * <p>
     * This endpoint allows clients to update an existing Oscar winner by specifying its unique identifier (ID)
     * and providing the updated Oscar winner details in the request body. If the update is successful, the
     * updated Oscar winner is returned. If no Oscar winner is found for the given ID, a "Not Found" response
     * is returned.
     *
     * @param id                    The unique identifier of the Oscar winner to update.
     * @param updatedOscarWinnerDto The updated Oscar winner details provided in the request body.
     * @return A {@code ResponseEntity} containing the updated Oscar winner if the update was successful,
     * or a "Not Found" response if no Oscar winner is found for the given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OscarWinnerDto>> updateOscarWinner(
            @PathVariable Long id,
            @RequestBody OscarWinnerDto updatedOscarWinnerDto
    ) {
        val oscarWinner = oscarWinnerMapper.dtoToEntity(updatedOscarWinnerDto);
        Optional<OscarWinner> updatedWinner = oscarWinnerService.updateOscarWinner(id, oscarWinner);

        return updatedWinner
                .map(winner -> {
                    OscarWinnerDto oscarWinnerDto = oscarWinnerMapper.entityToDTO(winner);
                    return apiResponseService.buildResponse(true, "Oscar winner updated successfully", oscarWinnerDto);
                })
                .orElseGet(() -> apiResponseService.buildErrorResponse(false, "Oscar winner not found for id: " + id));
    }

    /**
     * Endpoint to delete an Oscar winner by ID.
     * <p>
     * This endpoint allows clients to delete an Oscar winner by specifying its unique identifier (ID).
     * If the deletion is successful, a successful response with a message is returned. If no Oscar winner
     * is found for the given ID, an error response is returned.
     *
     * @param id The unique identifier of the Oscar winner to delete.
     * @return A {@code ResponseEntity} containing an {@code ApiResponse} indicating the success or failure
     * of the deletion.
     * - If the deletion is successful, a "200 OK" response with a success message is returned.
     * - If no Oscar winner is found for the given ID, a "404 Not Found" response with an error message is returned.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEntity<ApiResponse<Object>>> deleteOscarWinner(@PathVariable Long id) {
        Optional<Boolean> deletionSuccessful = oscarWinnerService.deleteOscarWinnerById(id);

        return deletionSuccessful
                .map(success -> {
                    if (success) {
                        return ResponseEntity.ok(apiResponseService.buildResponse(true, "Oscar winner deleted successfully", null));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(apiResponseService.buildErrorResponse(false, "No Oscar winner found for id: " + id));
                    }
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(apiResponseService.buildErrorResponse(false, "No Oscar winner found for id: " + id)));
    }


}

