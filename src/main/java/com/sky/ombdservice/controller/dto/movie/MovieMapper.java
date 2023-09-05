package com.sky.ombdservice.controller.dto.movie;

import com.sky.ombdservice.models.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto entityToDTO(Movie employee);

    List<MovieDto> entityToDTO(Iterable<Movie> employee);

    Movie dtoToEntity(MovieDto employee);

    List<Movie> dtoToEntity(Iterable<MovieDto> employees);


}
