package com.Library.LibraryApplication.Controllers;

import com.Library.LibraryApplication.Models.AppUser;
import com.Library.LibraryApplication.Models.RegisterDTO;
import com.Library.LibraryApplication.Repositories.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AccountController {
    @Autowired
    private AppUserRepository repo;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerDto", new RegisterDTO());
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register (
            Model model,
            @Valid @ModelAttribute("registerDto") RegisterDTO registerDto,
            BindingResult result
            ) {

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "confirmPassword", "Password and Confirm password do not match")
            );
        }

        AppUser appUser = repo.findByEmail(registerDto.getEmail());
        if (appUser!= null) {
            result.addError(
                    new FieldError("registerDto", "email", "Email address is already in use")
            );
        }

        if (result.hasErrors()){
            return "register";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setFirstName(registerDto.getFirstName());
            newUser.setLastName(registerDto.getLastName());
            newUser.setEmail(registerDto.getEmail());
            newUser.setRole("client");
            newUser.setCreatedAt(new Date());
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

            repo.save(newUser);

            model.addAttribute("registerDto", new RegisterDTO());
            model.addAttribute("success", true);
        }
        catch (Exception e) {
            result.addError(
                    new FieldError("registerDto", "firstName", e.getMessage())
            );
        }


        return "register";
    }
}
