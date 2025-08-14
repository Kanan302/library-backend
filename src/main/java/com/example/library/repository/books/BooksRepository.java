package com.example.library.repository.books;

import com.example.library.entity.books.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    List<Books> findByReadDate(LocalDate readDate);
}
