package com.Library.LibraryApplication.Repositories;

import com.Library.LibraryApplication.Models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
//    @Query(value = "SELECT * FROM Books where books.reserved = ?1", nativeQuery = true)
//    List<Reservation> findByReservation(int userID);
}
