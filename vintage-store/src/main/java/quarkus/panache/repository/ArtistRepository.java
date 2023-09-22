package quarkus.panache.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import quarkus.jdbc.Artist;

@ApplicationScoped
public class ArtistRepository implements PanacheRepository<Artist> {
}
