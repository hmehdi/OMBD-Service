package com.sky.ombdservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JacksonStdImpl
@Builder
@Entity
public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonProperty(value = "Response")
    private String response;

    @JsonProperty(value = "Title")
    private String title;

    @JsonProperty(value = "Year")
    private String year;

    @JsonProperty(value = "Rated")
    private String rated;

    @JsonProperty(value = "Released")
    private String released;

    @JsonProperty(value = "Runtime")
    private String runtime;

    @JsonProperty(value = "Genre")
    private String genre;

    @JsonProperty(value = "Director")
    private String director;

    @JsonProperty(value = "Writer")
    private String writer;

    @JsonProperty(value = "Actors")
    private String actors;

    @JsonProperty(value = "Plot")
    private String plot;

    @JsonProperty(value = "Language")
    private String language;

    @JsonProperty(value = "Country")
    private String country;

    @JsonProperty(value = "Awards")
    private String awards;

    @JsonProperty(value = "Poster")
    private String poster;

    @JsonProperty(value = "Metascore")
    private String metaScore;

    @JsonProperty(value = "imdbRating")
    private String imdbRating;

    @JsonProperty(value = "imdbVotes")
    private String imdbVotes;

    @JsonProperty(value = "imdbID")
    private String imdbId;

    @JsonProperty(value = "Type")
    private String type;

    @JsonProperty(value = "DVD")
    private String dvd;

    @JsonProperty(value = "BoxOffice")
    private String boxOffice;

    @JsonProperty(value = "Production")
    private String production;

    @JsonProperty(value = "Website")
    private String website;

    //  @JsonProperty(value = "Ratings")
    // private List<Rating> ratings;


    public Movie(String title, String year) {
        this.title = title;
        this.year = year;
    }

    public Movie(Long id, String title, String year) {
        this.id=id;
        this.title = title;
        this.year = year;
    }
}