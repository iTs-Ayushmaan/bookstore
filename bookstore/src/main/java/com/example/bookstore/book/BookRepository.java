package com.example.bookstore.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //SELECT * FROM book WHERE isbn = ?
    Optional<Book> findBookByisbn(String isbn);
    //SELECT * FROM book WHERE author = ?
    List<Book> findBooksByAuthor(String author);

    List<Book> findBooksByGenre(String genre);




}
