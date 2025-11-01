package com.example.bookstore.publisher;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // 1. Get all publishers
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    // 2. Add new publisher (check name uniqueness)
    public void addNewPublisher(Publisher publisher) {
        Optional<Publisher> publisherOptional = publisherRepository
                .findPublisherByName(publisher.getName());

        if (publisherOptional.isPresent()) {
            throw new IllegalStateException(
                    "Publisher with name " + publisher.getName() + " already exists!"
            );
        }

        publisherRepository.save(publisher);
    }

    // 3. Delete publisher (prevent if has books)
    public void deletePublisher(Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new IllegalStateException(
                        "Publisher with id " + publisherId + " does not exist"
                ));

        // Prevent deletion if publisher has books
        if (!publisher.getBooks().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete publisher with existing books! " +
                            "Publisher has " + publisher.getBooks().size() + " books."
            );
        }

        publisherRepository.delete(publisher);
    }

    // 4. Update publisher
    @Transactional
    public void updatePublisher(Long publisherId,
                                String name,
                                String address,
                                String website,
                                String email,
                                Integer foundedYear) {

        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new IllegalStateException(
                        "Publisher with id " + publisherId + " does not exist"
                ));

        // Update name (check uniqueness)
        if (name != null && name.length() > 0 && !name.equals(publisher.getName())) {
            Optional<Publisher> publisherOptional = publisherRepository
                    .findPublisherByName(name);
            if (publisherOptional.isPresent()) {
                throw new IllegalStateException("Publisher name already taken");
            }
            publisher.setName(name);
        }

        // Update address
        if (address != null && address.length() > 0 && !address.equals(publisher.getAddress())) {
            publisher.setAddress(address);
        }

        // Update website
        if (website != null && website.length() > 0 && !website.equals(publisher.getWebsite())) {
            publisher.setWebsite(website);
        }

        // Update email
        if (email != null && email.length() > 0 && !email.equals(publisher.getEmail())) {
            publisher.setEmail(email);
        }

        // Update foundedYear
        if (foundedYear != null && !foundedYear.equals(publisher.getFoundedYear())) {
            publisher.setFoundedYear(foundedYear);
        }
    }

    // 5. Get publisher with books loaded
    public Publisher getPublisherWithBooks(Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new IllegalStateException(
                        "Publisher with id " + publisherId + " does not exist"
                ));

        // Force load books (triggers lazy loading)
        publisher.getBooks().size();

        return publisher;
    }

    // 6. Search by founded year range
    public List<Publisher> searchByFoundedYearRange(Integer startYear, Integer endYear) {
        return publisherRepository.findPublishersByFoundedYearBetween(startYear, endYear);
    }

    // 7. Get publishers with books
    public List<Publisher> getPublishersWithBooks() {
        return publisherRepository.findPublishersWithBooks();
    }

    // 8. Get publishers without books
    public List<Publisher> getPublishersWithoutBooks() {
        return publisherRepository.findPublishersWithoutBooks();
    }

    // 9. Count books for a publisher
    public Long countPublisherBooks(Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            throw new IllegalStateException(
                    "Publisher with id " + publisherId + " does not exist"
            );
        }
        return publisherRepository.countBooksByPublisherId(publisherId);
    }
}
