package hr.tvz.spotlist.spotlistapp.controller;

import hr.tvz.spotlist.spotlistapp.model
        .ReviewCommand;
import hr.tvz.spotlist.spotlistapp.dto.ReviewDTO;
import hr.tvz.spotlist.spotlistapp.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<ReviewDTO>> getByPlaylistId(@PathVariable Long playlistId) {
        return ResponseEntity.ok(reviewService.findByPlaylistId(playlistId));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> add(@Valid @RequestBody ReviewCommand command) {
        ReviewDTO added = reviewService.add(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable Long id,
                                            @Valid @RequestBody ReviewCommand command) {
        return reviewService.update(id, command)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = reviewService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }
}