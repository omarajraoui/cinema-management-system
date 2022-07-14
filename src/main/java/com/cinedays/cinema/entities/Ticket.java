package com.cinedays.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nomClient;
    private double prix;
    @Column(unique = true, nullable = true)
    private Integer codePayement;
    private boolean reservee;

    @ManyToOne
    private ProjectionFilm projectionFilm;

    @ManyToOne
    private Place place;
}
