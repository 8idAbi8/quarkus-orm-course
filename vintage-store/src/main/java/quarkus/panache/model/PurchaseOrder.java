package quarkus.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import quarkus.jpa.Customer;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_purchase_orders")
public class PurchaseOrder extends PanacheEntity {

    @Column(name = "purchase_order_date", nullable = false)
    public LocalDate date = LocalDate.now();

    /* one instance of the entity on one side (the "one" side) can be related to multiple instances of the other entity (the "many" side).(1 orderline for item bought)
       By default this relationship is mapped with join table. If we don't want to use a joinTable, we can add the "mappedBy" attribute in the OneToMany annotation. */
    /* In JPA, the cascade attribute within the @OneToMany or @ManyToOne annotations
       is used to specify the cascade operations that should be applied to associated entities
       when a particular operation (e.g., persist, remove) is performed on the owning entity.
       The orphanRemoval ensures that if an associated entity is removed from the association,  it should be considered "orphaned" and hence removed from the database.*/
    @OneToMany(mappedBy = "purchaseOrder", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, orphanRemoval = true)
    public List<OrderLine> orderLines = new ArrayList<>();


    /* Many purchase order can belong to one customer, one customer can have many purchase orders.
     *  By default this relationship is mapped by a join column,
     *  so JPA will map this relationship with the customer_id column */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_fk")
    public Customer customer;

    @Column(name = "created_date", nullable = false)
    public Instant createdDate = Instant.now();

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.purchaseOrder = this;
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.purchaseOrder = null;
    }
}
