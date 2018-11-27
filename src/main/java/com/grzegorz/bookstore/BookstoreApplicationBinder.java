package com.grzegorz.bookstore;

import com.grzegorz.bookstore.service.BookService;
import com.grzegorz.bookstore.storage.BookStorage;
import com.grzegorz.bookstore.storage.InMemoryBookStorage;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class BookstoreApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(BookService.class).to(BookService.class);
        bind(InMemoryBookStorage.class).to(BookStorage.class);
    }
}
