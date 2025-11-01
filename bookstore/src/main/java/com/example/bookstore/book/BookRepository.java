package com.example.bookstore.book;

import com.example.bookstore.book.Book;
import com.example.bookstore.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // EXISTING METHODS (Keep these!)
    Optional<Book> findBookByIsbn(String isbn);
    List<Book> findBooksByAuthor(String author);
    List<Book> findBooksByGenre(String genre);
    List<Book> findBooksByPriceBetween(Double minPrice, Double maxPrice);
    List<Book> findBooksByTitleContaining(String keyword);

    // NEW: Find books by publisher ID
    List<Book> findBooksByPublisherId(Long publisherId);

    // NEW: Find books by publisher name
    List<Book> findBooksByPublisherName(String publisherName);

    // NEW: Find books without a publisher
    List<Book> findBooksByPublisherIsNull();

    // NEW: Find books with a publisher
    List<Book> findBooksByPublisherIsNotNull();

    // NEW: Find books with publisher info (avoid N+1)
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.publisher")
    List<Book> findAllBooksWithPublisher();

    // NEW: Find books by publisher and genre
    @Query("SELECT b FROM Book b WHERE b.publisher.name = :publisherName AND b.genre = :genre")
    List<Book> findBooksByPublisherAndGenre(
            @Param("publisherName") String publisherName,
            @Param("genre") String genre
    );

    // NEW: Find expensive books by publisher
    @Query("SELECT b FROM Book b WHERE b.publisher.id = :publisherId AND b.price > :minPrice")
    List<Book> findExpensiveBooksByPublisher(
            @Param("publisherId") Long publisherId,
            @Param("minPrice") Double minPrice
    );
}