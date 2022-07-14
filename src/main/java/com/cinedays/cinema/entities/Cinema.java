package com.cinedays.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Cinema {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name ;
    private  double longitude,latitude,altitude;
    private int nombreSalles;

    @ManyToOne
    private Ville ville;

    @OneToMany(mappedBy = "cinema")
    private Collection<Salle> salles;

}
