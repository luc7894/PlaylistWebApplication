package hr.tvz.spotlist.spotlistapp.controller;

import hr.tvz.spotlist.spotlistapp.model.Playlist;
import hr.tvz.spotlist.spotlistapp.model.PlaylistCommand;
import hr.tvz.spotlist.spotlistapp.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;


    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    @GetMapping
    public ResponseEntity<List<Playlist>> getPlaylists() {
        System.out.println("CONTROLLER CALLED");
        return ResponseEntity.ok(playlistService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        return playlistService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = playlistService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



    @PostMapping
    public ResponseEntity<Playlist> addPlaylist(@Valid @RequestBody PlaylistCommand command) {
        if (playlistService.duplicateCheckByTitle(command.getTitle())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }
        Playlist added = playlistService.add(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(added); // 201
    }


    // UPDATE ali s PUT

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> update(@PathVariable Long id,
                                           @Valid @RequestBody PlaylistCommand command) {
        return playlistService.update(id, command)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





}
