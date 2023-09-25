package quarkus.panache.repository;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import quarkus.jpa.Customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*@QuarkusTest: the test class requires the Quarkus application context to be set up for the test to run.
* Quarkus sets up the application context, including initializing dependencies, configurations, and other relevant components.*/
@QuarkusTest
public class CustomerRepositoryTest {

    @Inject
    CustomerRepository repository;


    @Test   // JUnit annotation used to indicate that the method is a test method that should be executed by the testing framework (JUnit) during the test run.
    @TestTransaction  /* if the persist method doesn't have one @Transactional annotation, the changes in the database will suffer rollback in the end of the test() method. !? */
    public void shouldCreateAndFindCustomer() {  // The method annotated with @Test must have a public access modifier, a void return type, and no parameters.

        Customer customer = new Customer("firstName", "lastName", "email");

        repository.persist(customer);
        assertNotNull(customer.getId());

        customer = repository.findById(customer.getId());
        assertEquals("firstName", customer.getFirstName());

        /*Typically, within a @Test method, you use various assertion methods (e.g., assertEquals, assertTrue, etc.) to validate the expected outcomes of the code being tested.*/
    }
}