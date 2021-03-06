package com.cinedays.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String titre;
    private double duree;
    private String realisateur;
    private String description;
    private String photo;
    @Temporal(TemporalType.DATE)
    private Date dateSortie;
    @ManyToOne
    private Categorie categorie;
    @OneToMany(mappedBy = "film")
    //when changed to json i dont want les projections de films
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<ProjectionFilm> projectionFilms;





}
