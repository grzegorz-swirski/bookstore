package com.grzegorz.bookstore.storage;

import com.grzegorz.bookstore.core.BookDetails;
import com.grzegorz.bookstore.core.BookEntity;

import java.util.List;

public interface BookStorage {

    List<BookEntity> findAll();

    List<BookEntity> findByAuthor(String author);

    List<BookEntity> findByTitle(String title);

    BookEntity findById(String id);

    BookEntity save(BookDetails value);
}
