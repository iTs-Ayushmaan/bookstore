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
            @RequestParam(required = false) Integer availableCopies
    ) {
        bookService.updateBook(bookId, title, author, price, availableCopies);
    }

    @GetMapping(path = "search/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return bookService.searchBooksByAuthor(author);
    }

    @GetMapping(path = "search/genre")
    public List<Book> searchBooksByGenre(@RequestParam String genre) {
        return bookService.searchBooksByGenre(genre);
    }
}
