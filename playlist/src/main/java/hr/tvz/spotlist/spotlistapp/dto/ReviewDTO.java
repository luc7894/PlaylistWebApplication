package hr.tvz.spotlist.spotlistapp.dto;

import lombok.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewDTO {
    private Long id;
    private Long playlistId;
    private String playlistTitle;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewedAt;
}

