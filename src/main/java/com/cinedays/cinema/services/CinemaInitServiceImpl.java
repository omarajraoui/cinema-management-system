package com.cinedays.cinema.services;

import com.cinedays.cinema.entities.*;
import com.cinedays.cinema.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SalleRepository salleRepository;


    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void initVilles() {
        Stream.of("Casablanca" , "Marrakech " ,"Tanger","Meknes" , "Rabat").forEach(villeNom->{
            Ville ville = new Ville();
            ville.setName(villeNom);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v->{
            Stream.of("Megarama" , "dawliz " ,"OmarCiné","cineDays","CineMax").forEach(cine->{
                Cinema cinema = new Cinema();
                cinema.setName(cine);
                cinema.setNombreSalles(3+(int) Math.random()*7);
                cinema.setVille(v);
                cinemaRepository.save(cinema);

            });
        });
    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema->{
            for(int i=0;i<cinema.getNombreSalles();i++){
                Salle salle =new Salle();
                salle.setName("Salle" + (i+1));
                salle.setCinema(cinema);
                salle.setNombrePlaces(20+(int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {
            for(int i=0;i<salle.getNombrePlaces();i++){
            Place place =new Place();
            place.setNumero((i+1));
            place.setSalle(salle);
            placeRepository.save(place);
        }
        });
    }

    @Override
    public void initSeances() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Stream.of("12:00" , "15:00" ,"17:00","19:00","21:00").forEach(seanceHeure->{
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(seanceHeure));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

    });
    }



    @Override
    public void initCategories() {
        Stream.of("Histoire" , "Action" ,"Drama","Fiction").forEach(categoryName -> {
            Categorie categorie = new Categorie();
            categorie.setName(categoryName);
            categorieRepository.save(categorie);


        });

    }

    @Override
    public void initFilms() {
        double [] durees = new double[] { 1,1.5,2,2.5,3 };
        List<Categorie> categories = categorieRepository.findAll();
        Stream.of("hunting hill House" , "It 2" ,"Batman","Spiderman").forEach(movie -> {
            Film film = new Film();
            film.setTitre(movie);
            //pick one from the list
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(movie.replaceAll(" ",""));
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);


        });
    }



    @Override
    public void initProjections() {
        double[] prices = new double[]{30,50,60,70,80,90,100};
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    filmRepository.findAll().forEach(film -> {
                        seanceRepository.findAll().forEach(seance -> {
                            ProjectionFilm projection = new ProjectionFilm();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);
                        });

                    });

                });
            });
        });


    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(projection -> {
            projection.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projection.getPrix());
                ticket.setProjectionFilm(projection);
                ticket.setReservee(false);
                ticketRepository.save(ticket);


            });

        });


    }
}
