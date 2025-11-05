package com.example.bookstore.book;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBooks(){
        return bookService.getAllBooks();
    }

    @PostMapping
    public void registerNewBook(@Valid @RequestBody Book book){
        bookService.addNewBook(book);
    }

    @DeleteMapping(path = "{BookId}")
    public void deleteBook(@PathVariable("BookId") Long bookId){
        bookService.deleteBook(bookId);
    }

    @PutMapping(path = "{bookId}")
    public void updateBook(
            @PathVariable("bookId") Long bookId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer availableCopies,
            @RequestParam(required = false) Long publisherId
    ) {
        bookService.updateBook(bookId, title, author, price, availableCopies,publisherId);
    }

    @GetMapping(path = "search/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return bookService.searchBooksByAuthor(author);
    }

    @GetMapping(path = "search/genre")
    public List<Book> searchBooksByGenre(@RequestParam String genre) {
        return bookService.searchBooksByGenre(genre);
    }

    // 7. PUT assign publisher to book (NEW)
    @PutMapping(path = "{bookId}/publisher/{publisherId}")
    public void assignPublisherToBook(
            @PathVariable("bookId") Long bookId,
            @PathVariable("publisherId") Long publisherId
    ) {
        bookService.assignPublisherToBook(bookId, publisherId);
    }

    // 8. DELETE remove publisher from book (NEW)
    @DeleteMapping(path = "{bookId}/publisher")
    public void removePublisherFromBook(@PathVariable("bookId") Long bookId) {
        bookService.removePublisherFromBook(bookId);
    }

    // 9. GET books by publisher (NEW)
    @GetMapping(path = "publisher/{publisherId}")
    public List<Book> getBooksByPublisher(@PathVariable("publisherId") Long publisherId) {
        return bookService.getBooksByPublisher(publisherId);
    }

    // 10. GET books without publisher (NEW)
    @GetMapping(path = "without-publisher")
    public List<Book> getBooksWithoutPublisher() {
        return bookService.getBooksWithoutPublisher();
    }

    // 11. GET search by publisher name and genre (NEW)
    @GetMapping(path = "search/publisher-genre")
    public List<Book> searchByPublisherAndGenre(
            @RequestParam String publisher,
            @RequestParam String genre
    ) {
        return bookService.searchBooksByPublisherAndGenre(publisher, genre);
    }
}
