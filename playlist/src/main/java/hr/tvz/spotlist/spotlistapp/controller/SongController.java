package hr.tvz.spotlist.spotlistapp.controller;

import hr.tvz.spotlist.spotlistapp.dto.SongDTO;

import hr.tvz.spotlist.spotlistapp.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/songs")
public class SongController {


    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/playlist/{playlistId}")
    public ResponseEntity<List<SongDTO>> getByPlaylistId(@PathVariable Long playlistId) {
        return ResponseEntity.ok(songService.findByPlaylistId(playlistId));
    }
}
