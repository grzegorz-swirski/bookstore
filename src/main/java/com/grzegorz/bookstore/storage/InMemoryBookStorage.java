package com.grzegorz.bookstore.storage;

import com.grzegorz.bookstore.core.BookDetails;
import com.grzegorz.bookstore.core.BookEntity;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryBookStorage implements BookStorage {

    private final Map<String, BookEntity> bookMap;

    public InMemoryBookStorage() {
        this.bookMap = new HashMap<>();
        save(new BookDetails("Sample Author1", "Sample Title1"));
        save(new BookDetails("Sample Author2", "Sample Title2"));
    }

    @Override
    public List<BookEntity> findAll() {
        return new ArrayList<>(bookMap.values());
    }

    @Override
    public List<BookEntity> findByAuthor(String author) {
        return findMatching(b -> b.getBook().getAuthor().equals(author));
    }

    private List<BookEntity> findMatching(Predicate<? super BookEntity> predicate) {
        return bookMap.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookEntity> findByTitle(String title) {
        return findMatching(b -> b.getBook().getTitle().equals(title));
    }

    @Override
    public BookEntity findById(String id) {
        return bookMap.get(id);
    }

    @Override
    public BookEntity save(BookDetails value) {
        String id = UUID.randomUUID().toString();
        BookEntity entity = new BookEntity(id, value);
        bookMap.put(id, entity);
        return entity;
    }
}
