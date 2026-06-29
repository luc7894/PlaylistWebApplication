package hr.tvz.spotlist.spotlistapp.service;

import hr.tvz.spotlist.spotlistapp.model.Playlist;
import hr.tvz.spotlist.spotlistapp.model.PlaylistCommand;
import hr.tvz.spotlist.spotlistapp.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);


    }
    @Override
    public Playlist add(Playlist playlist) {
        return playlistRepository.add(playlist);
    }


    @Override
    public boolean duplicateCheckByTitle(String title) {
        return playlistRepository.duplicateCheckByTitle(title);
    }

    @Override
    public Playlist add(PlaylistCommand command) {
        Playlist playlist = Playlist.builder()
                .id((long) (playlistRepository.findAll().size() + 1))
                .title(command.getTitle())
                .description(command.getDescription())
                .genre(command.getGenre())
                .mood(command.getMood())
                .isPublic(command.getIsPublic())
                .createdAt(LocalDateTime.now())
                .build();
        return playlistRepository.add(playlist);
    }


    @Override
    public Optional<Playlist> update(Long id, PlaylistCommand command) {
        return playlistRepository.update(id, command);
    }

    @Override
    public boolean deleteById(Long id) {
        return playlistRepository.deleteById(id);
    }





}
