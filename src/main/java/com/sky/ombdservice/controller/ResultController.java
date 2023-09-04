package com.sky.ombdservice.controller;


import com.sky.ombdservice.constant.Path;
import com.sky.ombdservice.constant.Template;
import com.sky.ombdservice.models.Movie;
import com.sky.ombdservice.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ResultController {
    private final MovieService movieService;

    @PostMapping(value = Path.RESULT)
    public String result(@RequestParam("title") final String movieTitle, final Model model) {

        final var response = movieService.find(movieTitle);

        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return Template.NOT_FOUND;

        var result = movieService.find(movieTitle).getBody();
        model.addAttribute("movie", movieService.find(movieTitle).getBody());
        return Template.RESULT;
    }


}
