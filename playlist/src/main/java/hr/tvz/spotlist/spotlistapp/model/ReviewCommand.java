package hr.tvz.spotlist.spotlistapp.model;

import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReviewCommand {

    @NotNull(message = "Playlist ID je obavezan")
    private Long playlistId;

    @NotNull(message = "Ocjena je obavezna")
    @Min(value = 1, message = "Ocjena mora biti najmanje 1")
    @Max(value = 5, message = "Ocjena mora biti najviše 5")
    private Integer rating;

    @Size(max = 500, message = "Komentar ne smije biti duži od 500 znakova")
    private String comment;
}