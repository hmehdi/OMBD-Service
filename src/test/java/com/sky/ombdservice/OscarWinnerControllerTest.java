package com.sky.ombdservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sky.ombdservice.controller.OscarWinnerController;
import com.sky.ombdservice.models.OscarWinner;
import com.sky.ombdservice.service.OscarWinnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OscarWinnerController.class)
@AutoConfigureMockMvc
public class OscarWinnerControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OscarWinnerService oscarWinnerService;

    private OscarWinner oscarWinner;
    private List<OscarWinner> oscarWinnerList;

    @BeforeEach
    void setUp() {
        oscarWinner = new OscarWinner(1L, "Movie 1", "2022", "Best Actor");
        oscarWinnerList = new ArrayList<>();
        oscarWinnerList.add(oscarWinner);
    }

    @Test
    public void testGetAllOscarWinners() throws Exception {
        Mockito.when(oscarWinnerService.getAllOscarWinners()).thenReturn(oscarWinnerList);

        mockMvc.perform(get("/api/oscar-winners/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Movie 1"));
    }

    @Test
    public void testGetOscarWinnerById() throws Exception {
        Mockito.when(oscarWinnerService.getOscarWinnerById(1L)).thenReturn(Optional.of(oscarWinner));

        mockMvc.perform(get("/api/oscar-winners/{id}", 926L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Movie 1"));
    }

    @Test
    public void testGetOscarWinnerById_NotFound() throws Exception {
        Mockito.when(oscarWinnerService.getOscarWinnerById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/oscar-winners/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddOscarWinner() throws Exception {
        String oscarWinnerJson = objectMapper.writeValueAsString(oscarWinner);

        Mockito.when(oscarWinnerService.addOscarWinner(any(OscarWinner.class))).thenReturn(oscarWinner);

        mockMvc.perform(post("/api/oscar-winners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(oscarWinnerJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Movie 1"));
    }

//    @Test
//    public void testUpdateOscarWinner() throws Exception {
//        String oscarWinnerJson = objectMapper.writeValueAsString(oscarWinner);
//
//        Mockito.when(oscarWinnerService.updateOscarWinner(eq(926L), any(OscarWinner.class)))
//                .thenReturn(Optional.of(oscarWinner));
//
//        mockMvc.perform(put("/api/oscar-winners/{id}", 926L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(oscarWinnerJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value("Movie 1"));
//    }


//    @Test
//    public void testDeleteOscarWinner() throws Exception {
//        Mockito.when(oscarWinnerService.deleteOscarWinner(1L)).thenReturn(true);
//
//        mockMvc.perform(delete("/api/oscar-winners/{id}", 1L))
//                .andExpect(status().isNoContent());
//    }

    @Test
    public void testGetOscarWinnersByAward() throws Exception {
        Mockito.when(oscarWinnerService.getOscarWinnersByAward("Best Actor")).thenReturn(oscarWinnerList);

        mockMvc.perform(get("/api/oscar-winners/by-award/{award}", "Best Actor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Movie 1"));
    }

    @Test
    public void testGetOscarWinnersByMovie() throws Exception {
        Mockito.when(oscarWinnerService.getOscarWinnersByMovie("Movie 1")).thenReturn(oscarWinnerList);

        mockMvc.perform(get("/api/oscar-winners/by-movie/{movie}", "Movie 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Movie 1"));
    }
}

