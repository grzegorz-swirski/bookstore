package com.grzegorz.bookstore;

import org.glassfish.jersey.server.ResourceConfig;

public class BookstoreApplication extends ResourceConfig {

    public BookstoreApplication() {
        register(new BookstoreApplicationBinder());
        packages("com.grzegorz.bookstore.rest");
    }
}
