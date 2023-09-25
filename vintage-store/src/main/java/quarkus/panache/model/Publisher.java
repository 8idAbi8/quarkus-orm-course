package quarkus.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

/* To construct a Panache entity:
* 1. Extend the PanacheEntity class provided by Panache.
*    This class contains useful methods for working with JPA entities, such as persisting, updating, deleting, and querying.
* 2. The @Entity annotation marks the Java class as a persistent entity, representing a database table.
*
* By default, Panache entities have an automatically generated identifier (ID) for each instance.
* */
@Entity
@Table(name = "t_publishers")
public class Publisher extends PanacheEntity { // Publisher is

    @Column(length = 50, nullable = false)
    public String name;
    @Column(name = "created_date", nullable = false)
    public Instant createdDate = Instant.now();

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }
}


