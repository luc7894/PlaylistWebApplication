package hr.tvz.spotlist.spotlistapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "playlist")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "genre")
    private String genre;

    @Column(name = "mood")
    private String mood;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<Song> songs;



}
