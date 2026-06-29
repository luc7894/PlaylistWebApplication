package hr.tvz.spotlist.spotlistapp.service;

import hr.tvz.spotlist.spotlistapp.model.ReviewCommand;
import hr.tvz.spotlist.spotlistapp.dto.ReviewDTO;
import hr.tvz.spotlist.spotlistapp.model.Playlist;
import hr.tvz.spotlist.spotlistapp.model.Review;
import hr.tvz.spotlist.spotlistapp.repository.PlaylistJpaRepository;
import hr.tvz.spotlist.spotlistapp.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaylistJpaRepository playlistJpaRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             PlaylistJpaRepository playlistJpaRepository) {
        this.reviewRepository = reviewRepository;
        this.playlistJpaRepository = playlistJpaRepository;
    }

    @Override
    public List<ReviewDTO> findByPlaylistId(Long playlistId) {
        return reviewRepository.findByPlaylistId(playlistId)
                .stream()
                .map(r -> ReviewDTO.builder()
                        .id(r.getId())
                        .playlistId(r.getPlaylist().getId())
                        .rating(r.getRating())
                        .comment(r.getComment())
                        .reviewedAt(r.getReviewedAt())
                        .build())
                .toList();
    }

    @Override
    public ReviewDTO add(ReviewCommand command) {
        Playlist playlist = playlistJpaRepository
                .findById(command.getPlaylistId())
                .orElseThrow(() -> new RuntimeException("Playlist nije pronađen!"));

        Review review = Review.builder()
                .playlist(playlist)
                .rating(command.getRating())
                .comment(command.getComment())
                .reviewedAt(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        return ReviewDTO.builder()
                .id(saved.getId())
                .playlistId(saved.getPlaylist().getId())
                .rating(saved.getRating())
                .comment(saved.getComment())
                .reviewedAt(saved.getReviewedAt())
                .build();
    }

    @Override
    public Optional<ReviewDTO> update(Long id, ReviewCommand command) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setRating(command.getRating());
                    review.setComment(command.getComment());
                    Review saved = reviewRepository.save(review);
                    return ReviewDTO.builder()
                            .id(saved.getId())
                            .playlistId(saved.getPlaylist().getId())
                            .rating(saved.getRating())
                            .comment(saved.getComment())
                            .reviewedAt(saved.getReviewedAt())
                            .build();
                });
    }

    @Override
    public boolean delete(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
    //10

    @Override
    public List<ReviewDTO> getTopPlaylistsByRating(int limit) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        return reviewRepository.findTopByRatingLastSevenDays(sevenDaysAgo)
                .stream()
                .sorted((r1, r2) -> r2.getRating().compareTo(r1.getRating()))
                .limit(limit)
                .map(r -> ReviewDTO.builder()
                        .playlistTitle(r.getPlaylist().getTitle())
                        .rating(r.getRating())
                        .build())
                .toList();
    }
}