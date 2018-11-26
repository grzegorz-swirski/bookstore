package com.grzegorz.bookstore.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class BookCollection {

    private Collection<BookEntity> books;
}
