package com.sky.ombdservice.controller.dto.movie;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class MovieDto {
    private Long id;
    @NonNull
    @NotBlank(message = "You do have a title, don't you?")
    @Size(min = 3, max = 20)
    private String title;

    @NonNull
    @NotBlank(message = "require")
    @Size(min = 3, max = 20)
    private String year;
}