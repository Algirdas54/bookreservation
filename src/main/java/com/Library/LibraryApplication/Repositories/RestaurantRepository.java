package com.Library.LibraryApplication.Repositories;

import com.Library.LibraryApplication.Models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

}
