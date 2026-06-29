package hr.tvz.spotlist.spotlistapp.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistCommand {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String genre;

    @NotBlank
    private String mood;

    @NotNull
    private Boolean isPublic;
}