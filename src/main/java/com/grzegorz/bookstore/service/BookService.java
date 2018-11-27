package com.grzegorz.bookstore.service;

import com.grzegorz.bookstore.core.BookDetails;
import com.grzegorz.bookstore.core.BookEntity;
import com.grzegorz.bookstore.storage.BookStorage;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class BookService {

    private BookStorage bookStorage;

    @Inject
    public BookService(BookStorage bookStorage) {
        this.bookStorage = bookStorage;
    }

    public List<BookEntity> getAll() {
        return bookStorage.findAll();
    }

    public List<BookEntity> getByAuthor(String author) {
        return bookStorage.findByAuthor(author);
    }

    public List<BookEntity> getByTitle(String title) {
        return bookStorage.findByTitle(title);
    }

    public BookEntity create(BookDetails value) {
        return bookStorage.save(value);
    }

    public Optional<BookEntity> getById(String id) {
        return Optional.ofNullable(bookStorage.findById(id));
    }
}
