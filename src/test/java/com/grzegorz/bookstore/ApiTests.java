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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ApiTests {

    // TODO: use Jersey test framework for integration testing
    private BookApi api;

    @Before
    public void setup() throws URISyntaxException {
        UriInfo uriInfoMock = Mockito.mock(UriInfo.class);
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
        BookEntity createdBook = postSampleBook();
        String id = createdBook.getId();
        assertEquals(createdBook, api.getById(id).getEntity());
    }

    private BookEntity postSampleBook() {
        String author = "author-" + UUID.randomUUID().toString();
        String title = "title-" + UUID.randomUUID().toString();
        BookDetails sampleDetails = new BookDetails(author, title);
        return (BookEntity) api.create(sampleDetails).getEntity();
    }

    @Test
    public void createBooks_getAll_returnsAllCreatedBooks() throws Exception {
        BookEntity sample1 = postSampleBook();
        BookEntity sample2 = postSampleBook();
        List<BookEntity> samples = Arrays.asList(sample1, sample2);

        BookCollection booksCollection = (BookCollection) api.getAll().getEntity();

        assertTrue(booksCollection.getBooks().containsAll(samples));
    }
}
