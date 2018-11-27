package com.grzegorz.bookstore.rest;

import com.grzegorz.bookstore.core.BookCollection;
import com.grzegorz.bookstore.core.BookDetails;
import com.grzegorz.bookstore.core.BookEntity;
import com.grzegorz.bookstore.service.BookService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;

@Path("/books")
@AllArgsConstructor
@NoArgsConstructor
public class BookApi {

    // TODO: return Response objects
    // TODO: check out DI in Jersey
    private final BookService bookService = new BookService();

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .ok(new BookCollection(bookService.getAll()))
                .build();
    }

    @GET
    @Path("/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") String id) {
        Optional<BookEntity> optionalBook = bookService.getById(id);
        if (optionalBook.isPresent()) {
            return Response
                    .ok(optionalBook.get())
                    .build();
        }
        return Response
                .status(HttpURLConnection.HTTP_NOT_FOUND)
                .build();
    }

    @GET
    @Path("/author/{author}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByAuthor(@PathParam("author") String author) {
        List<BookEntity> booksByAuthor = bookService.getByAuthor(author);
        return Response
                .ok(new BookCollection(booksByAuthor))
                .build();
    }


    @GET
    @Path("/title/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByTitle(@PathParam("title") String title) {
        List<BookEntity> booksByTitle = bookService.getByTitle(title);
        return Response
                .ok(new BookCollection(booksByTitle))
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(BookDetails body) {
        BookEntity bookEntity = bookService.create(body);
        return Response
                .created(uriInfo.getAbsolutePath())
                .entity(bookEntity)
                .build();
    }
}
