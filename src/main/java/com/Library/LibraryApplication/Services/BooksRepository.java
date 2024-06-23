package com.Library.LibraryApplication.Services;

import com.Library.LibraryApplication.modules.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
