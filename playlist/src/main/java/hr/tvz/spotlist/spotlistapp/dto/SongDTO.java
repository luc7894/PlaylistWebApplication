package hr.tvz.spotlist.spotlistapp.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SongDTO {

    private Long id;

    private Long playlistId;
    private String name;

    private String artist;

    private String album;

    private BigDecimal durationSeconds;
}
