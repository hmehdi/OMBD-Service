package com.sky.ombdservice;

import com.sky.ombdservice.controller.OscarWinnerController;
import com.sky.ombdservice.controller.dto.ApiResponse;
import com.sky.ombdservice.controller.dto.oscarwinner.OscarWinnerDto;
import com.sky.ombdservice.controller.dto.oscarwinner.OscarWinnerMapper;
import com.sky.ombdservice.controller.response.ApiResponseService;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.service.OscarWinnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OscarWinnerControllerTest {

    @InjectMocks
    private OscarWinnerController oscarWinnerController;

    @Mock
    private OscarWinnerService oscarWinnerService;

    @Mock
    private ApiResponseService apiResponseService;

    @Mock
    private OscarWinnerMapper oscarWinnerMapper;

    @Test
    public void givenOscarWinnersExist_whenGetAllOscarWinners_thenReturnListOfOscarWinners() {
        // Given
        List<OscarWinner> oscarWinners = Arrays.asList(
                new OscarWinner(1L, "Actor 1", "Movie 1", "Best Picture", "2022"),
                new OscarWinner(2L, "Actress 2", "Movie 2", "Best Picture", "2023")
        );
        List<OscarWinnerDto> oscarWinnerDtos = Arrays.asList(
                new OscarWinnerDto(1L, "Actor 1", "Movie 1", "Best Picture", "2022"),
                new OscarWinnerDto(2L, "Actress 2", "Movie 2", "Best Picture", "2023")
        );

        ApiResponse<List<OscarWinnerDto>> expectedApiResponse = new ApiResponse<>(true, "Oscar winners found", oscarWinnerDtos);
        ResponseEntity<ApiResponse<List<OscarWinnerDto>>> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);

        // Mock the behavior of oscarWinnerService and oscarWinnerMapper
        when(oscarWinnerService.getAllOscarWinners()).thenReturn(Optional.of(oscarWinners));
        when(oscarWinnerMapper.entityToDTO(oscarWinners)).thenReturn(oscarWinnerDtos);

        // Mock the behavior of apiResponseService
        when(apiResponseService.buildResponse(true, "Oscar winners found", oscarWinnerDtos)).thenReturn(expectedResponseEntity);

        // When
        ResponseEntity<ApiResponse<List<OscarWinnerDto>>> responseEntity = oscarWinnerController.getAllOscarWinners();

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildResponse(true, "Oscar winners found", oscarWinnerDtos);


    }

    @Test
    public void givenOscarWinnerExists_whenGetOscarWinnerById_thenReturnOscarWinnerDto() {
        // Given
        Long oscarWinnerId = 1L;
        OscarWinner oscarWinner =  new OscarWinner(oscarWinnerId, "Actor 1", "Movie 1", "Best Picture", "2022");
        OscarWinnerDto oscarWinnerDto =  new OscarWinnerDto(oscarWinnerId, "Actor 1", "Movie 1", "Best Picture", "2022");

        ApiResponse<OscarWinnerDto> expectedApiResponse = new ApiResponse<>(true, "Oscar winner found", oscarWinnerDto);
        ResponseEntity<ApiResponse<OscarWinnerDto>> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);

        // Mock the behavior of oscarWinnerService and oscarWinnerMapper
        when(oscarWinnerService.getOscarWinnerById(oscarWinnerId)).thenReturn(Optional.of(oscarWinner));
        when(oscarWinnerMapper.entityToDTO(oscarWinner)).thenReturn(oscarWinnerDto);

        // Mock the behavior of apiResponseService
        when(apiResponseService.buildResponse(true, "Oscar winner found", oscarWinnerDto)).thenReturn(expectedResponseEntity);

        // When
        ResponseEntity<ApiResponse<OscarWinnerDto>> responseEntity = oscarWinnerController.getOscarWinnerById(oscarWinnerId);

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildResponse(true, "Oscar winner found", oscarWinnerDto);
    }


    @Test
    public void givenValidMovieTitle_whenGetOscarWinnersByMovie_thenReturnListOfOscarWinners() {
        // Given
        String movieTitle = "Movie 1";
        List<OscarWinner> oscarWinners = Arrays.asList(
                new OscarWinner(1L, "Actor 1", movieTitle, "Best Picture", "2022"),
                new OscarWinner(2L, "Actress 2", movieTitle, "Best Director", "2023")
        );


        ApiResponse<List<OscarWinner>> expectedApiResponse = new ApiResponse<>(true, "Oscar winners retrieved", oscarWinners);
        ResponseEntity<ApiResponse<List<OscarWinner>>> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);

        // Mock the behavior of oscarWinnerService
        when(oscarWinnerService.getOscarWinnersByMovie(movieTitle)).thenReturn(Optional.of(oscarWinners));

        // Mock the behavior of apiResponseService
        when(apiResponseService.buildResponse(true, "Oscar winners retrieved", oscarWinners)).thenReturn(expectedResponseEntity);

        // When
        ResponseEntity<ApiResponse<List<OscarWinner>>> responseEntity = oscarWinnerController.getOscarWinnersByMovie(movieTitle);

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildResponse(true, "Oscar winners retrieved", oscarWinners);
    }
    @Test
    public void givenValidAwardName_whenGetOscarWinnersByAward_thenReturnListOfOscarWinners() {
        // Given
        String awardName = "Best Picture";
        List<OscarWinner> oscarWinners = Arrays.asList(
                new OscarWinner(1L, "Actor 1", "Movie 1", awardName, "2022"),
                new OscarWinner(2L, "Actress 2", "Movie 2", awardName, "2023")
        );


        ApiResponse<List<OscarWinner>> expectedApiResponse = new ApiResponse<>(true, "Oscar winners retrieved", oscarWinners);
        ResponseEntity<ApiResponse<List<OscarWinner>>> expectedResponseEntity = ResponseEntity.ok(expectedApiResponse);

        // Mock the behavior of oscarWinnerService
        when(oscarWinnerService.getOscarWinnersByAward(awardName)).thenReturn(Optional.of(oscarWinners));

        // Mock the behavior of apiResponseService
        when(apiResponseService.buildResponse(true, "Oscar winners retrieved", oscarWinners)).thenReturn(expectedResponseEntity);

        // When
        ResponseEntity<ApiResponse<List<OscarWinner>>> responseEntity = oscarWinnerController.getOscarWinnersByAward(awardName);

        // Then
        assertThat(responseEntity).isEqualTo(expectedResponseEntity);
        verify(apiResponseService).buildResponse(true, "Oscar winners retrieved", oscarWinners);
    }

}

