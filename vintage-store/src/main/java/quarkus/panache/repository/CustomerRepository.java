package quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.jpa.Customer;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {
}
