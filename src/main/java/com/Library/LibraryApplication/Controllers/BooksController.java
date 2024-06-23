package com.Library.LibraryApplication.Controllers;

import com.Library.LibraryApplication.Services.BooksRepository;
import com.Library.LibraryApplication.modules.BookDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.Library.LibraryApplication.modules.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksRepository repo;

    @GetMapping({"", "/"})
    public String showBookList(Model model) {
        List<Book> books = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("books", books);
        return "books/index";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        BookDTO bookDto = new BookDTO();
        model.addAttribute("bookDto", new BookDTO());
        return "books/AddBook";
    }
    @PostMapping("/create")
    public String createBook(
            @Valid @ModelAttribute("bookDto") BookDTO bookDto,
            BindingResult result
            ) {
        if (bookDto.getImageFile().isEmpty()){
            result.addError(new FieldError("bookDto", "imageFile", "The image file is required"));
        }

        if (result.hasErrors()){
            return "books/AddBook";
        }

        MultipartFile image = bookDto.getImageFile();
        Date createdAt = new Date();
        String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()){
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setCategory(bookDto.getCategory());
        book.setCreatedAt(createdAt);
        book.setImageFileName(storageFileName);
        book.setDescription(bookDto.getDescription());

        repo.save(book);

        return "redirect:/books";
    }
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
            ){

        try {
            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            BookDTO bookDto = new BookDTO();
            bookDto.setName(book.getName());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setCategory(book.getCategory());
            bookDto.setDescription(book.getDescription());

            model.addAttribute("bookDto", bookDto);
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
            @Valid @ModelAttribute("bookDto") BookDTO bookDto,
            BindingResult result
    ){

        try {
            Book book = repo.findById(id).get();
            model.addAttribute("book", book);

            if (result.hasErrors()){
                return "books/EditBook";
            }

            if(!bookDto.getImageFile().isEmpty()){
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + book.getImageFileName());

                try {
                    Files.delete(oldImagePath);
                }
                catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                MultipartFile image = bookDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }

                book.setImageFileName(storageFileName);
            }

            book.setName(bookDto.getName());
            book.setAuthor(bookDto.getAuthor());
            book.setCategory(bookDto.getCategory());
            book.setDescription(bookDto.getDescription());

            repo.save(book);

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
            Book book = repo.findById(id).get();

            Path imagePath = Paths.get("public/images/" + book.getImageFileName());

            try {
                Files.delete(imagePath);
            }
            catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            repo.delete(book);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return "redirect:/books";
    }
}
