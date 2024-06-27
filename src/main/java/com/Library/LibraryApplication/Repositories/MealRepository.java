package com.Library.LibraryApplication.Repositories;

import com.Library.LibraryApplication.Models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {

}
