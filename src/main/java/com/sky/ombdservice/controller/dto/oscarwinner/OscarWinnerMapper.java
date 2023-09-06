package com.sky.ombdservice.controller.dto.oscarwinner;

import com.sky.ombdservice.controller.dto.oscarwinner.OscarWinnerDto;
import com.sky.ombdservice.models.OscarWinner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OscarWinnerMapper {

    OscarWinnerDto entityToDTO(OscarWinner employee);

    List<OscarWinnerDto> entityToDTO(Iterable<OscarWinner> employee);

    OscarWinner dtoToEntity(OscarWinnerDto employee);

    List<OscarWinner> dtoToEntity(Iterable<OscarWinnerDto> employees);


}