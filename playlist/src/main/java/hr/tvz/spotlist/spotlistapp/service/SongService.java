package hr.tvz.spotlist.spotlistapp.service;
import hr.tvz.spotlist.spotlistapp.dto.SongDTO;
import hr.tvz.spotlist.spotlistapp.model.Song;

import java.util.List;


public interface SongService {

    List<SongDTO> findByPlaylistId(Long playlistId);

    List<SongDTO> findLast5();

}
