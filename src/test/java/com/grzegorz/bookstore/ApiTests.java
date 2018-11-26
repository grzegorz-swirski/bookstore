package com.grzegorz.bookstore;

import com.grzegorz.bookstore.core.BookCollection;
import com.grzegorz.bookstore.core.BookDetails;
import com.grzegorz.bookstore.core.BookEntity;
import com.grzegorz.bookstore.rest.BookApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ApiTests {

    private UriInfo uriInfoMock;

    // TODO: use Jersey test framework for integration testing
    private BookApi api;

    @Before
    public void setup() throws URISyntaxException {
        uriInfoMock = Mockito.mock(UriInfo.class);
        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI("http://sample/path/"));
        api = new BookApi(uriInfoMock);
    }

    @Test
    public void getByAuthor_doesNotExist_returnsEmptyOk() throws Exception {
        Response r = api.getByAuthor(UUID.randomUUID().toString());
        assertEmptyOkResponse(r);
    }

    private void assertEmptyOkResponse(Response r) {
        BookCollection responseBody = (BookCollection) r.getEntity();
        assertEquals(HttpURLConnection.HTTP_OK, r.getStatus());
        assertEquals(0, responseBody.getBooks().size());
    }

    @Test
    public void getByTitle_doesNotExist_returnsEmptyOk() throws Exception {
        Response r = api.getByTitle(UUID.randomUUID().toString());
        assertEmptyOkResponse(r);
    }

    @Test
    public void getById_doesNotExist_returnsNotFound() throws Exception {
        Response r = api.getById(UUID.randomUUID().toString());
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, r.getStatus());
    }

    @Test
    public void createBook_getById_returnsRequestedBook() throws Exception {
        String author = UUID.randomUUID().toString();
        String title = UUID.randomUUID().toString();
        BookDetails details = new BookDetails(author, title);

        BookEntity createdBook = (BookEntity) api.create(details).getEntity();
        String id = createdBook.getId();

        assertEquals(createdBook, api.getById(id).getEntity());
    }
}
