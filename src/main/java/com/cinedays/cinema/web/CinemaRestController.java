package com.cinedays.cinema.web;

import com.cinedays.cinema.entities.Film;
import com.cinedays.cinema.entities.Ticket;
import com.cinedays.cinema.repository.FilmRepository;
import com.cinedays.cinema.repository.TicketRepository;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CinemaRestController {

     private FilmRepository filmRepository;
     private TicketRepository ticketRepository;

    public CinemaRestController(FilmRepository filmRepository, TicketRepository ticketRepository) {
        this.filmRepository = filmRepository;
        this.ticketRepository = ticketRepository;
    }

    //img donne binaire
    //navigator gets bytes but specify that it gives a jpeg img
    @GetMapping(path="/imageFilm/{id}", produces=MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable (name="id")Long id) throws Exception{
        Film film = filmRepository.findById(id).get();
        String photoName = film.getPhoto();
        File file = new File(System.getProperty("user.home")+"./CinemaImages/images/"+photoName);
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @PostMapping("/payerTickets")
    @Transactional
    public List<Ticket> payerTickes(@RequestBody TicketForm ticketForm){
        List <Ticket> listeTickets =new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket->{
            Ticket ticket =ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setReservee(true);
            ticket.setCodePayement(ticketForm.getCodePayement());
            ticketRepository.save(ticket);
            listeTickets.add(ticket);

        });
        return listeTickets;
    }



}




@Data
class TicketForm{
    private String nomClient;
    private int codePayement;
    private List<Long> tickets =new ArrayList<>();
}