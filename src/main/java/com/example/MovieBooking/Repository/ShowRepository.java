package com.example.MovieBooking.Repository;

import com.example.MovieBooking.entity.Show;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Optional<List<Show>> findByMovieId(Long movieId);
    Optional<List<Show>> findByTheaterId(Long theaterId);
}
