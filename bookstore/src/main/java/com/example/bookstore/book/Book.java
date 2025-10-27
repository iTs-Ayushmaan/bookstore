package com.example.bookstore.book;


import com.example.bookstore.publisher.Publisher;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@Entity
@Table
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Author cannot be blank")
    private String author;

    @Column(unique = true,nullable = false)
    private String isbn;
    @Positive
    private Double price;

    @Min(value = 1000)
    private int publicationYear;

    private  String genre;
    @PositiveOrZero
    private int availableCopies;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id",nullable = true)
    @JsonBackReference
    private Publisher publisher;

    public Book(){}

    public Book(String author, String title, String isbn, int availableCopies, Double price, int publicationYear, String genre) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.availableCopies = availableCopies;
        this.price = price;
        this.publicationYear = publicationYear;
        this.genre = genre;
    }

    public Book(String title, String author, String isbn, Double price, int publicationYear, String genre, int availableCopies, Publisher publisher) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.availableCopies = availableCopies;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", price=" + price +
                ", publicationYear=" + publicationYear +
                ", genre='" + genre + '\'' +
                ", availableCopies=" + availableCopies +
                ", publisher=" + publisher + (publisher!=null ? publisher.getName() : "None") +
                '}';
    }
}
