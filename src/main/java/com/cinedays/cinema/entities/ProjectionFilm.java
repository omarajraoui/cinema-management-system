package com.cinedays.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class ProjectionFilm {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id ;
        @Temporal(TemporalType.TIMESTAMP)
        private Date dateProjection;
        private double prix;

        @ManyToOne
        private Salle salle;

        @ManyToOne
        private Film film;


        @ManyToOne
        private Seance seance;

        @OneToMany(mappedBy = "projectionFilm")
        private Collection<Ticket> tickets;






}
