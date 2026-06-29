package hr.tvz.spotlist.spotlistapp.repository;

import hr.tvz.spotlist.spotlistapp.model.Song;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByPlaylistId(Long playlistId);
    @Query("SELECT s FROM Song s ORDER BY s.id DESC")
    List<Song> findTop5ByOrderByIdDesc();
}
