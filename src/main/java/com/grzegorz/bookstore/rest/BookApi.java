package com.grzegorz.bookstore.rest;

import com.grzegorz.bookstore.core.BookCollection;
import com.grzegorz.bookstore.core.BookDetails;
import com.grzegorz.bookstore.core.BookEntity;
import com.grzegorz.bookstore.service.BookService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;

@Path("/books")
public class BookApi {

    // TODO: return Response objects
    // TODO: check out DI in Jersey
    private BookService bookService = new BookService();

    @Context
    private UriInfo uriInfo;

    public BookApi(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .ok(bookService.getAll())
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
    @Path("/")
    public Response create(BookDetails body) {
        BookEntity bookEntity = bookService.create(body);
        return Response
                .created(uriInfo.getAbsolutePath())
                .entity(bookEntity)
                .build();
    }
}
