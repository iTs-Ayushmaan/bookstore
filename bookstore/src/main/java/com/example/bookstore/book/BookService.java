package com.example.bookstore.book;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addNewBook(Book book){
        Optional<Book> bookOptional = bookRepository.findBookByisbn(book.getIsbn());
        if(bookOptional.isPresent()){
            throw new IllegalStateException("Book with ISBN " + book.getIsbn() + " already exists!");
        }
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId){
        boolean exists = bookRepository.existsById(bookId);
        if (!exists){
            throw new IllegalStateException("Book with id " + bookId + " does not exist");
        }
        bookRepository.deleteById(bookId);
    }

    @Transactional
    public void updateBook(Long bookId,
                           String title,
                           String author,
                           Double price,
                           Integer availableCopies){
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "Book with id " + bookId + " does not exist"
                ));

        if (title != null && title.length() > 0 && !Objects.equals(book.getTitle(), title)) {
            book.setTitle(title);
        }

        if (author != null && author.length() > 0 && !Objects.equals(book.getAuthor(), author)) {
            book.setAuthor(author);
        }

        if (price != null && price > 0 && !Objects.equals(book.getPrice(), price)) {
            book.setPrice(price);
        }

        if (availableCopies != null && availableCopies >= 0 && !Objects.equals(book.getAvailableCopies(), availableCopies)) {
            book.setAvailableCopies(availableCopies);
        }

    }

    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    public List<Book> searchBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre);
    }

}
