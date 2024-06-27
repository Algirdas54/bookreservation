package com.Library.LibraryApplication.Repositories;

import com.Library.LibraryApplication.Models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Query(value = "SELECT * FROM meals where meals.restaurant_id = ?1", nativeQuery = true)
    List<Meal> findByRestaurantID(int restaurant_id);

}
