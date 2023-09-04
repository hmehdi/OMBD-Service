package com.sky.ombdservice.models;


import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@JacksonStdImpl
@Builder
@Entity
@Table(name = "oscar_winners")
public class OscarWinner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "oscar_no")
    private Long oscarNo;

    @Column(name = "oscar_yr")
    private String oscarYear;

    @Column(name = "award")
    private String award;

    @Column(name = "name")
    private String name;

    @Column(name = "movie")
    private String movie;

    @Column(name = "age")
    private int age;

    @Column(name = "birth_pl")
    private String birthPlace;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "birth_mo")
    private String birthMonth;

    @Column(name = "birth_d")
    private String birthDay;

    @Column(name = "birth_y")
    private String birthYear;

    public OscarWinner() {
        // Default constructor required by JPA
    }

    public OscarWinner(long id,String name, String oscarYear, String award) {
        this.id= id;
        this.name = name;
        this.oscarYear = oscarYear;
        this.award = award;
    }


    public boolean isBestPictureWinner() {
        return "Best Picture".equals(award);
    }


}

