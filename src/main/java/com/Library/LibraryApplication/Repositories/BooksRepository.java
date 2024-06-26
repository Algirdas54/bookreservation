package com.Library.LibraryApplication.Repositories;

import com.Library.LibraryApplication.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM Books where books.reserved = ?1", nativeQuery = true)
    List<Book> findByReservation(int userID);
}
