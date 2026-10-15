package com.example.MovieBooking.service;

import com.example.MovieBooking.Repository.MovieRepository;
import com.example.MovieBooking.Repository.ShowRepository;
import com.example.MovieBooking.Repository.TheaterRepository;
import com.example.MovieBooking.dto.ShowDTO;
import com.example.MovieBooking.entity.Booking;
import com.example.MovieBooking.entity.Movie;
import com.example.MovieBooking.entity.Show;
import com.example.MovieBooking.entity.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public Show createShow(ShowDTO showDTO) {
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(()-> new RuntimeException("No movie found id " + showDTO.getMovieId()));

        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(()-> new RuntimeException("No theater found " + showDTO.getTheaterId()));

        return showRepository.save(
                Show.builder()
                        .showTime(showDTO.getShowTime())
                        .price(showDTO.getPrice())
                        .movie(movie)
                        .theater(theater)
                        .build()
        );
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId) {
        Optional<List<Show>> showListBox = showRepository.findByMovieId(movieId);
        if(showListBox.isPresent()) {
            return showListBox.get();
        }
        else throw new RuntimeException("No shows available for the movie");
    }

    public List<Show> getShowsByTheater(Long theaterId){
        Optional<List<Show>> showListBox = showRepository.findByTheaterId(theaterId);
        if(showListBox.isPresent()) {
            return showListBox.get();
        }
        else throw new RuntimeException("No shows available for the Theater");
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Show available for the id " + id));

        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No Movie Found for id " + showDTO.getMovieId()));

        Theater theater = theaterRepository.findById(showDTO.getTheaterId())
                .orElseThrow(() -> new RuntimeException("No Theater Found for id " + showDTO.getTheaterId()));

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheater(theater);

        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        if(!showRepository.existsById(id)) {
            throw new RuntimeException("No Show available for the id "+id);
        }

        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if(bookings.isEmpty()) {
            throw new RuntimeException("Can't delete show with existing bookings");
        }

        showRepository.deleteById(id);
    }
}
