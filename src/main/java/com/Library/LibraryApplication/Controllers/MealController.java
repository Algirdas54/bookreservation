package com.Library.LibraryApplication.Controllers;

import com.Library.LibraryApplication.Models.*;
import com.Library.LibraryApplication.Repositories.AppUserRepository;
import com.Library.LibraryApplication.Repositories.MealRepository;
import com.Library.LibraryApplication.Repositories.RestaurantRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.Library.LibraryApplication.Models.Restaurant;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class MealController {

    private int resid;

    @Autowired
    private MealRepository repo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @GetMapping({"/meals"})
    public String showMenu(Model model, @RequestParam int id) {
        Restaurant restaurant = restaurantRepo.findById(id).get();
        List<Meal> meals = repo.findByRestaurantID(id);
        model.addAttribute("meals", meals);
        model.addAttribute("restaurant", restaurant);
        return "restaurants/meals";
    }
    @GetMapping("/createmeal")
    public String showCreatePage(Model model, @RequestParam int id) {
        MealDTO mealDTO = new MealDTO();
        resid = id;
        model.addAttribute("mealDTO", mealDTO);
        return "restaurants/createmeal";
    }
    @PostMapping("/createmeal")
    public String createMeal(
            @Valid @ModelAttribute("mealDTO") MealDTO mealDTO,
            BindingResult result
    ) {


        if (result.hasErrors()){
            return "restaurants/createmeal";
        }

        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setDescription(mealDTO.getDescription());
        meal.setRestaurant_id(resid);

        repo.save(meal);

        return "redirect:/restaurants";
    }
    @GetMapping("/editmeal")
    public String showEditMeal(
            Model model,
            @RequestParam int id
    ){

        try {
            Meal meal = repo.findById(id).get();
            model.addAttribute("meal", meal);

            MealDTO mealDTO = new MealDTO();
            mealDTO.setName(meal.getName());
            mealDTO.setDescription(meal.getDescription());

            model.addAttribute("mealDTO", mealDTO);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/restaurants";
        }

        return "restaurants/editmeal";
    }
    @PostMapping("/editmeal")
    public String updateMeal(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute("mealDTO") MealDTO mealDTO,
            BindingResult result
    ){

        try {
            Meal meal = repo.findById(id).get();
            model.addAttribute("meal", meal);

            if (result.hasErrors()){
                return "restaurants/editmeal";
            }

            meal.setName(mealDTO.getName());
            meal.setDescription(mealDTO.getDescription());

            repo.save(meal);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/restaurants";
    }
    @GetMapping("/deletemeal")
    public String deleteMeal(
            @RequestParam int id
    ) {

        try {
            Meal meal = repo.findById(id).get();

            repo.delete(meal);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/restaurants";
    }

}
