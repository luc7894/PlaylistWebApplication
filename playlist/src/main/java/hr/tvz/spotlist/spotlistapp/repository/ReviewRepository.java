package hr.tvz.spotlist.spotlistapp.repository;

import hr.tvz.spotlist.spotlistapp.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPlaylistId(Long playlistId);

    @Query("SELECT r FROM Review r WHERE r.reviewedAt >= :sevenDaysAgo ORDER BY r.rating DESC")
    List<Review> findTopByRatingLastSevenDays(@Param("sevenDaysAgo") LocalDateTime sevenDaysAgo);
}