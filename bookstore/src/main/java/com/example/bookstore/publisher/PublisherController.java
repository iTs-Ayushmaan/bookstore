package com.example.bookstore.publisher;

import com.example.bookstore.book.Book;
import com.example.bookstore.book.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/publisher")
public class PublisherController {

    private final PublisherService publisherService;
    private final BookService bookService;

    @Autowired
    public PublisherController(PublisherService publisherService,
                               BookService bookService){
        this.publisherService = publisherService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<Publisher> getPublisher(){
        return publisherService.getAllPublishers();
    }

    @GetMapping(path ="{publisherId}")
    public Publisher getPublisher(@PathVariable("publisherId") Long publisherId){
        return publisherService.getPublisherWithBooks(publisherId);
    }

    @PostMapping
    public void registerNewPublisher(@Valid @RequestBody Publisher publisher){
        publisherService.addNewPublisher(publisher);
    }

    // 4. DELETE publisher
    @DeleteMapping(path = "{publisherId}")
    public void deletePublisher(@PathVariable("publisherId") Long publisherId) {
        publisherService.deletePublisher(publisherId);
    }

    // 5. PUT update publisher
    @PutMapping(path = "{publisherId}")
    public void updatePublisher(
            @PathVariable("publisherId") Long publisherId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String website,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer foundedYear
    ) {
        publisherService.updatePublisher(publisherId, name, address, website, email, foundedYear);
    }

    // 6. GET publisher's books
    @GetMapping(path = "{publisherId}/books")
    public List<Book> getPublisherBooks(@PathVariable("publisherId") Long publisherId) {
        return bookService.getBooksByPublisher(publisherId);
    }

    // 7. GET count of publisher's books
    @GetMapping(path = "{publisherId}/books/count")
    public Long countPublisherBooks(@PathVariable("publisherId") Long publisherId) {
        return publisherService.countPublisherBooks(publisherId);
    }

    // 8. GET search publishers by year range
    @GetMapping(path = "search")
    public List<Publisher> searchPublishers(
            @RequestParam Integer startYear,
            @RequestParam Integer endYear
    ) {
        return publisherService.searchByFoundedYearRange(startYear, endYear);
    }

    // 9. GET publishers with books
    @GetMapping(path = "with-books")
    public List<Publisher> getPublishersWithBooks() {
        return publisherService.getPublishersWithBooks();
    }

    // 10. GET publishers without books
    @GetMapping(path = "without-books")
    public List<Publisher> getPublishersWithoutBooks() {
        return publisherService.getPublishersWithoutBooks();
    }
}
