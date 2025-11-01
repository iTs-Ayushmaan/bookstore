package com.example.bookstore.book;
//
//import com.example.demo.publisher.Publisher;
//import com.example.demo.publisher.PublisherRepository;
import com.example.bookstore.publisher.Publisher;
import com.example.bookstore.publisher.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;  // NEW!

    @Autowired
    public BookService(BookRepository bookRepository,
                       PublisherRepository publisherRepository) {  // NEW!
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    // 1. Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // 2. Add new book (updated with publisher validation)
    public void addNewBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(book.getIsbn());
        if (bookOptional.isPresent()) {
            throw new IllegalStateException(
                    "Book with ISBN " + book.getIsbn() + " already exists!"
            );
        }

        // Verify publisher exists if provided
        if (book.getPublisher() != null) {
            Long publisherId = book.getPublisher().getId();
            if (publisherId != null && !publisherRepository.existsById(publisherId)) {
                throw new IllegalStateException(
                        "Publisher with id " + publisherId + " does not exist"
                );
            }
        }

        bookRepository.save(book);
    }

    // 3. Add new book with publisher ID
    public void addNewBookWithPublisher(Book book, Long publisherId) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(book.getIsbn());
        if (bookOptional.isPresent()) {
            throw new IllegalStateException(
                    "Book with ISBN " + book.getIsbn() + " already exists!"
            );
        }

        if (publisherId != null) {
            Publisher publisher = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Publisher with id " + publisherId + " does not exist"
                    ));
            book.setPublisher(publisher);
        }

        bookRepository.save(book);
    }

    // 4. Delete book
    public void deleteBook(Long bookId) {
        boolean exists = bookRepository.existsById(bookId);
        if (!exists) {
            throw new IllegalStateException(
                    "Book with id " + bookId + " does not exist"
            );
        }
        bookRepository.deleteById(bookId);
    }

    // 5. Update book (updated with publisher)
    @Transactional
    public void updateBook(Long bookId,
                           String title,
                           String author,
                           Double price,
                           Integer availableCopies,
                           Long publisherId) {  // NEW PARAMETER

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "Book with id " + bookId + " does not exist"
                ));

        if (title != null && title.length() > 0 && !title.equals(book.getTitle())) {
            book.setTitle(title);
        }

        if (author != null && author.length() > 0 && !author.equals(book.getAuthor())) {
            book.setAuthor(author);
        }

        if (price != null && price > 0 && !price.equals(book.getPrice())) {
            book.setPrice(price);
        }

        if (availableCopies != null && availableCopies >= 0 &&
                !availableCopies.equals(book.getAvailableCopies())) {
            book.setAvailableCopies(availableCopies);
        }

        // Update publisher
        if (publisherId != null) {
            Publisher newPublisher = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Publisher with id " + publisherId + " does not exist"
                    ));

            if (book.getPublisher() == null ||
                    !book.getPublisher().getId().equals(publisherId)) {
                book.setPublisher(newPublisher);
            }
        }
    }

    // 6. Assign publisher to book
    @Transactional
    public void assignPublisherToBook(Long bookId, Long publisherId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "Book with id " + bookId + " does not exist"
                ));

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new IllegalStateException(
                        "Publisher with id " + publisherId + " does not exist"
                ));

        book.setPublisher(publisher);
    }

    // 7. Remove publisher from book
    @Transactional
    public void removePublisherFromBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalStateException(
                        "Book with id " + bookId + " does not exist"
                ));

        book.setPublisher(null);
    }

    // 8. Search by author
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findBooksByAuthor(author);
    }

    // 9. Search by genre
    public List<Book> searchBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre);
    }

    // 10. Get books by publisher
    public List<Book> getBooksByPublisher(Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new IllegalStateException(
                    "Publisher with id " + publisherId + " does not exist"
            );
        }
        return bookRepository.findBooksByPublisherId(publisherId);
    }

    // 11. Get books without publisher
    public List<Book> getBooksWithoutPublisher() {
        return bookRepository.findBooksByPublisherIsNull();
    }

    // 12. Get books by publisher name
    public List<Book> getBooksByPublisherName(String publisherName) {
        return bookRepository.findBooksByPublisherName(publisherName);
    }

    // 13. Search books by publisher and genre
    public List<Book> searchBooksByPublisherAndGenre(String publisherName, String genre) {
        return bookRepository.findBooksByPublisherAndGenre(publisherName, genre);
    }
}
