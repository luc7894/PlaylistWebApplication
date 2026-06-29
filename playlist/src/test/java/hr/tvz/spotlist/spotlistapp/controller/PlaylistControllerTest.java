package hr.tvz.spotlist.spotlistapp.controller;

import hr.tvz.spotlist.spotlistapp.model.Playlist;
import hr.tvz.spotlist.spotlistapp.model.PlaylistCommand;
import hr.tvz.spotlist.spotlistapp.security.JwtAuthenticationFilter;
import hr.tvz.spotlist.spotlistapp.security.JwtTokenProvider;
import hr.tvz.spotlist.spotlistapp.security.UserDetailsServiceImpl;
import hr.tvz.spotlist.spotlistapp.service.PlaylistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlaylistController.class)
@AutoConfigureMockMvc(addFilters = false)
class PlaylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlaylistService playlistService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private UserDetailsServiceImpl userDetailsService;







    private Playlist testPlaylist() {
        return Playlist.builder()
                .id(1L)
                .title("D&b")
                .description("Drum and bass")
                .genre("Electronic")
                .mood("High energetic")
                .isPublic(true)
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Test
    @WithMockUser
    void getPlaylists() throws Exception {
        when(playlistService.findAll()).thenReturn(List.of(testPlaylist()));

        mockMvc.perform(get("/api/playlists"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title").value("D&b"));
    }

    @Test
    void getPlaylistById_200() throws Exception {
        when(playlistService.findById(1L))
                .thenReturn(Optional.of(testPlaylist()));

        mockMvc.perform(get("/api/playlists/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("D&b"));
    }

    @Test
    void getPlaylistById_404() throws Exception {
        when(playlistService.findById(999L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/playlists/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    //post dio

    @Test
    @WithMockUser
    @DirtiesContext
    void addPlaylist_201() throws Exception {
        when(playlistService.duplicateCheckByTitle(any())).thenReturn(false);
        when(playlistService.add(any(PlaylistCommand.class)))
                .thenReturn(testPlaylist());

        mockMvc.perform(post("/api/playlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "D&b",
                        "description": "Drum and bass",
                        "genre": "Electronic",
                        "mood": "High energetic",
                        "isPublic": true
                    }
                    """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("D&b"));
    }

    //los post

    @Test
    @WithMockUser
    @DirtiesContext
    void addPlaylist_400() throws Exception {

        mockMvc.perform(post("/api/playlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "",
                        "description": "",
                        "genre": "",
                        "mood": "",
                        "isPublic": null
                    }
                    """))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //depete

    @Test
    @WithMockUser
    @DirtiesContext
    void deleteById_204() throws Exception {
        when(playlistService.deleteById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/playlists/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser
    @DirtiesContext
    void deleteById_404() throws Exception {
        when(playlistService.deleteById(anyLong())).thenReturn(false);

        mockMvc.perform(delete("/api/playlists/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


  //dodala ako ima duplikata test jer nije bilo 880&%

    @Test
    @WithMockUser
    void addPlaylist_409() throws Exception {
        when(playlistService.duplicateCheckByTitle(any())).thenReturn(true);

        mockMvc.perform(post("/api/playlists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "title": "D&b",
                        "description": "Drum and bass",
                        "genre": "Electronic",
                        "mood": "High energetic",
                        "isPublic": true
                    }
                    """))
                .andExpect(status().isConflict());
    }

}