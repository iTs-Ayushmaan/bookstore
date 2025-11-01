package com.example.bookstore.publisher;

import com.example.bookstore.publisher.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    // Find publisher by name (for duplicate checking)
    Optional<Publisher> findPublisherByName(String name);

    // Find publishers founded in a specific period
    List<Publisher> findPublishersByFoundedYearBetween(Integer startYear, Integer endYear);

    // Find all publishers with their books (avoids N+1 problem)
    @Query("SELECT DISTINCT p FROM Publisher p LEFT JOIN FETCH p.books")
    List<Publisher> findAllPublishersWithBooks();

    // Count how many books a publisher has
    @Query("SELECT COUNT(b) FROM Book b WHERE b.publisher.id = :publisherId")
    Long countBooksByPublisherId(@Param("publisherId") Long publisherId);

    // Find publishers with at least one book
    @Query("SELECT DISTINCT p FROM Publisher p WHERE SIZE(p.books) > 0")
    List<Publisher> findPublishersWithBooks();

    // Find publishers without any books
    @Query("SELECT p FROM Publisher p WHERE SIZE(p.books) = 0")
    List<Publisher> findPublishersWithoutBooks();
}