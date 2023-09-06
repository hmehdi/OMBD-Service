package com.sky.ombdservice.controller.dto.oscarwinner;

import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OscarWinnerDto {
    private Long id;

    @NonNull
    @NotBlank(message = "You do have a name, don't you?")
    @Size(min = 3, max = 20)
    private String name;

    @NonNull
    @NotBlank(message = "require")
    @Size(min = 3, max = 4)
    private String oscarYear;

    @Size(min = 3, max = 20)
    private String award;


    private Long oscarNo;


    @NonNull
    @NotBlank(message = "You do have a movie, don't you?")
    @Size(min = 3, max = 20)
    private String movie;


    public OscarWinnerDto(long id, String name, String movie, String award,String oscarYear) {
        this.id= id;
        this.name= name;
        this.movie= movie;
        this.award= award;
        this.oscarYear= oscarYear;
    }
}