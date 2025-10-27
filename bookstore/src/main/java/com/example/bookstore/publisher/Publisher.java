package com.example.bookstore.publisher;


import com.example.bookstore.book.Book;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Publisher {
    @Id
    @SequenceGenerator(
            name = "publisher_sequence",
            sequenceName = "publisher_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "publisher_sequence"
    )
    private Long id;

    @NotBlank(message = "Publisher name is required")
    @Column(unique = true)
    private String name;

    private String address;

    private String website;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 1400, message = "Founded year must be after 1400")
    @Max(value = 2025, message = "Founded year cannot be in future")
    private Integer foundedYear;

    @OneToMany(
            mappedBy = "publisher",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();

    public Publisher() {}

    public Publisher(String name, String address, String website, String email, Integer foundedYear) {
        this.name = name;
        this.address = address;
        this.website = website;
        this.email = email;
        this.foundedYear = foundedYear;
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Helper methods for managing bidirectional relationship
    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setPublisher(null);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", foundedYear=" + foundedYear +
                ", books=" + books +
                '}';
    }
}
