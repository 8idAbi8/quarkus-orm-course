package quarkus.panache.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import quarkus.jdbc.Artist;
import quarkus.jpa.Customer;
import quarkus.panache.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class PurchaseOrderRepositoryTest {

    /* To persist the customer we need his repository, because customer is a jpa entity*/
    @Inject
    CustomerRepository customerRepository;
    @Inject
    ArtistRepository artistRepository;

    @Test
    @TestTransaction
    public void shouldCreateAndFindAPurchaseOrder() {
        long countCustomers = customerRepository.count();
        long countArtists = artistRepository.count();
        long countPublishers = Publisher.count();
        long countBooks = Book.count();
        long countPurchaseOrders = PurchaseOrder.count();
        long countOrderLines = OrderLine.count();

        // Creates a Customer
        Customer customer = new Customer();
        customer.setFirstName("first name");
        customer.setLastName("last name");
        customer.setEmail("email");
        // Creates an Artist
        Artist artist = new Artist();
        artist.setName("name");
        artist.setBio("bio");
        // Creates a Publisher
        Publisher publisher = new Publisher();
        publisher.name = "name";
        // Creates a Book
        Book book = new Book();
        book.title = "title";
        book.description = "description";
        book.price = new BigDecimal(10);
        book.isbn = "ISBN";
        book.nbOfPages = 500;
        book.publicationDate = LocalDate.now().minusDays(100);
        book.language = Language.ENGLISH;
        // Sets the Publisher and Artist to the Book
        book.publisher = publisher;
        book.artist = artist;
        // Persists the Book with one Publisher and one Artist
        Book.persist(book);

        // Creates a PurchaseOrder (pointing to the order line) with an OrderLine (pointing to the book)
        OrderLine orderLine = new OrderLine();
        orderLine.item = book;
        orderLine.quantity = 2;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        // Sets the Customer, Publisher, Artist and Book to the Purchase Order
        purchaseOrder.customer = customer;
        purchaseOrder.addOrderLine(orderLine);

        // Persists the PurchaseOrder and one OrderLine
        PurchaseOrder.persist(purchaseOrder);
        assertNotNull(purchaseOrder.id);
        assertEquals(1, purchaseOrder.orderLines.size());

        assertEquals(countCustomers + 1, customerRepository.count());
        assertEquals(countArtists + 1, artistRepository.count());
        assertEquals(countPublishers + 1, Publisher.count());
        assertEquals(countBooks + 1, Book.count());
        assertEquals(countPurchaseOrders + 1, PurchaseOrder.count());
        assertEquals(countOrderLines + 1, OrderLine.count());

        // Gets the PurchaseOrder
        purchaseOrder = PurchaseOrder.findById(purchaseOrder.id);

        // Deletes the PurchaseOrder
        PurchaseOrder.deleteById(purchaseOrder.id);
        assertEquals(countCustomers + 1, customerRepository.count());
        assertEquals(countArtists + 1, artistRepository.count());
        assertEquals(countPublishers + 1, Publisher.count());
        assertEquals(countBooks + 1, Book.count());
        assertEquals(countPurchaseOrders, PurchaseOrder.count());
        assertEquals(countOrderLines, OrderLine.count());
    }
}