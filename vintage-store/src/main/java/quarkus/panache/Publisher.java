package quarkus.panache;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.Instant;

/* To construct a Panache entity:
* 1. Extend the PanacheEntity class provided by Panache.
*    This class contains useful methods for working with JPA entities, such as persisting, updating, deleting, and querying.
* 2. The @Entity annotation marks the Java class as a persistent entity, representing a database table.
* */
@Entity
public class Publisher extends PanacheEntity { // Publisher is

    public String name;
    public Instant createdDate = Instant.now();

    public Publisher() {
    }

    public Publisher(String name) {
        this.name = name;
    }
}


