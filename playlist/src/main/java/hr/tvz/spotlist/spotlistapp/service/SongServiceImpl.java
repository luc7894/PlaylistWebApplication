package hr.tvz.spotlist.spotlistapp.service;

import hr.tvz.spotlist.spotlistapp.dto.SongDTO;

import hr.tvz.spotlist.spotlistapp.repository.SongRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;


    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;

    }

    @Override
    public List<SongDTO> findByPlaylistId(Long playlistId) {
        return songRepository.findByPlaylistId(playlistId)
                .stream()
                .map(s -> SongDTO.builder()
                        .id(s.getId())
                        .playlistId(s.getPlaylist().getId())
                        .name(s.getName())
                        .album(s.getAlbum())
                        .artist(s.getArtist())
                        .durationSeconds(s.getDurationSeconds())
                        .build())
                .toList();
    }

    @Override
    public List<SongDTO> findLast5() {
        return songRepository.findTop5ByOrderByIdDesc()
                .stream()
                .map(s -> SongDTO.builder()
                        .id(s.getId())
                        .playlistId(s.getPlaylist().getId())
                        .name(s.getName())
                        .album(s.getAlbum())
                        .artist(s.getArtist())
                        .durationSeconds(s.getDurationSeconds())
                        .build())
                .toList();
    }


}
