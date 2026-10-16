package com.example.MovieBooking.Repository;

import com.example.MovieBooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
