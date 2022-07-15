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
public class ProjectionFilm {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id ;
        @Temporal(TemporalType.TIMESTAMP)
        private Date dateProjection;
        private double prix;

        @ManyToOne
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private Salle salle;

        @ManyToOne
        private Film film;


        @ManyToOne
        private Seance seance;

        @OneToMany(mappedBy = "projectionFilm")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private Collection<Ticket> tickets;






}
