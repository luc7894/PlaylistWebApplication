package hr.tvz.spotlist.spotlistapp.repository;

import hr.tvz.spotlist.spotlistapp.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlaylistJpaRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByTitle(String title);
    Optional<Playlist> findByGenre(String genre);
}