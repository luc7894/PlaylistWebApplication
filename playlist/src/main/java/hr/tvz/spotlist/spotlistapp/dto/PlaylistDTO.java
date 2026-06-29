package hr.tvz.spotlist.spotlistapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaylistDTO {


    private String title;
    private String genre;
    private String mood;



}
