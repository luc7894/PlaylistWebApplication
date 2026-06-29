package hr.tvz.spotlist.spotlistapp.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SongCommand {


    @NotNull(message = "Playlist ID je obavezan")
    private Long playlistId;

    @NotBlank(message="Ime obavezn0o")
    private String name;

    @NotBlank(message="Ime izvodaca obavezn0o")
    private String artist;

    @NotBlank(message="Album obavezn0o")
    private String album;


    @NotNull(message = "Trajanje je obavezno")
    @DecimalMin(value = "0.0", inclusive = false, message = "Trajanje mora biti veće od 0")
    @DecimalMax(value = "3600.0", message = "Trajanje ne može biti duže od sat vremena")
    private BigDecimal durationSeconds;

}
