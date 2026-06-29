package hr.tvz.spotlist.spotlistapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "song")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @Column(name = "name")
    private String name;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;


    @Column(name="durationSeconds")
    private BigDecimal durationSeconds;

}
