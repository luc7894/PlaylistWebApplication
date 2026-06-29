package hr.tvz.spotlist.spotlistapp.repository;


import hr.tvz.spotlist.spotlistapp.model.Playlist;
import hr.tvz.spotlist.spotlistapp.model.PlaylistCommand;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository {

    List<Playlist> findAll();
    Optional<Playlist> findById(Long id);

    Playlist add(Playlist playlist);

    boolean duplicateCheckByTitle(String title);

    Optional<Playlist> update(Long id, PlaylistCommand command);


    boolean deleteById(Long id);


}
