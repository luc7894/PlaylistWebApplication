package hr.tvz.spotlist.spotlistapp.service;
import hr.tvz.spotlist.spotlistapp.model.ReviewCommand;
import hr.tvz.spotlist.spotlistapp.dto.ReviewDTO;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<ReviewDTO> findByPlaylistId(Long playlistId);
    ReviewDTO add(ReviewCommand command);

    Optional<ReviewDTO> update(Long id, ReviewCommand command);
    boolean delete(Long id);
    List<ReviewDTO> getTopPlaylistsByRating(int limit);
}