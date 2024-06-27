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

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/books")
public class MealController {

    @Autowired
    private MealRepository repo;

    @GetMapping({"/meals"})
    public String showRestaurantList(Model model) {
        List<Meal> meals = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("meals", meals);
        return "books/meals";
    }
    @GetMapping("/createmeal")
    public String showCreatePage(Model model) {
        model.addAttribute("mealDTO", new MealDTO());
        return "books/createmeal";
    }
    @PostMapping("/createmeal")
    public String createMeal(
            @Valid @ModelAttribute("mealDTO") MealDTO mealDTO,
            BindingResult result
    ) {


        if (result.hasErrors()){
            return "books/createmeal";
        }

        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setDescription(mealDTO.getDescription());

        repo.save(meal);

        return "redirect:/books";
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
            return "redirect:/books";
        }

        return "books/editmeal";
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
                return "books/editmeal";
            }

            meal.setName(mealDTO.getName());
            meal.setDescription(mealDTO.getDescription());

            repo.save(meal);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/books";
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

        return "redirect:/books";
    }
//    @GetMapping("/bookinfo")
//    public String bookInfo(
//            Model model,
//            @RequestParam int id
//    ){
//
//        Restaurant restaurant = repo.findById(id).get();
//        model.addAttribute("restaurant", restaurant);
//
//        return "books/bookinfo";
//    }

//    @GetMapping({"/mybooks"})
//    public String showReservedBooks(Model model, Authentication authentication) {
//
//        AppUser user = accRepo.findByEmail(authentication.getName());
//        List<Book> books = repo.findByReservation(user.getId());
//
//        model.addAttribute("books", books);
//
//        return "books/mybooks";
//    }
}
