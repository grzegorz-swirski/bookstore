package com.grzegorz.bookstore.core;

import lombok.Data;

@Data
public class BookEntity {

    private final String id;
    private final BookDetails book;
}
