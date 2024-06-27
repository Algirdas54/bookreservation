package com.Library.LibraryApplication.Controllers;

import com.Library.LibraryApplication.Models.AppUser;
import com.Library.LibraryApplication.Models.Restaurant;
import com.Library.LibraryApplication.Repositories.AppUserRepository;
import com.Library.LibraryApplication.Repositories.RestaurantRepository;
import com.Library.LibraryApplication.Models.RestaurantDTO;
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
public class RestaurantController {

    @Autowired
    private RestaurantRepository repo;

    @Autowired
    private AppUserRepository accRepo;

    @GetMapping({"", "/"})
    public String showRestaurantList(Model model) {
        List<Restaurant> restaurants = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("restaurants", restaurants);
        return "books/index";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("restaurantDTO", new RestaurantDTO());
        return "books/AddBook";
    }
    @PostMapping("/create")
    public String createRestaurant(
            @Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
            BindingResult result
            ) {


        if (result.hasErrors()){
            return "books/AddBook";
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setCode(restaurantDTO.getCode());
        restaurant.setAddress(restaurantDTO.getAddress());

        repo.save(restaurant);

        return "redirect:/books";
    }
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
            ){

        try {
            Restaurant restaurant = repo.findById(id).get();
            model.addAttribute("restaurant", restaurant);

            RestaurantDTO restaurantDTO = new RestaurantDTO();
            restaurantDTO.setName(restaurant.getName());
            restaurantDTO.setCode(restaurant.getCode());
            restaurantDTO.setAddress(restaurant.getAddress());

            model.addAttribute("restaurantDTO", restaurantDTO);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/books";
        }

        return "books/EditBook";
    }
    @PostMapping("/edit")
    public String updateBook(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute("restaurantDTO") RestaurantDTO restaurantDTO,
            BindingResult result
    ){

        try {
            Restaurant restaurant = repo.findById(id).get();
            model.addAttribute("restaurant", restaurant);

            if (result.hasErrors()){
                return "books/EditBook";
            }

            restaurant.setName(restaurantDTO.getName());
            restaurant.setCode(restaurantDTO.getCode());
            restaurant.setAddress(restaurantDTO.getAddress());

            repo.save(restaurant);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/books";
    }
    @GetMapping("/delete")
    public String deleteBook(
            @RequestParam int id
            ) {

        try {
            Restaurant restaurant = repo.findById(id).get();

            repo.delete(restaurant);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/books";
    }
    @GetMapping("/bookinfo")
    public String bookInfo(
            Model model,
            @RequestParam int id
            ){

        Restaurant restaurant = repo.findById(id).get();
        model.addAttribute("restaurant", restaurant);

        return "books/bookinfo";
    }

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
