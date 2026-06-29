package hr.tvz.spotlist.spotlistapp.controller;

import hr.tvz.spotlist.spotlistapp.dto.ReviewDTO;
import hr.tvz.spotlist.spotlistapp.model.Playlist;

import hr.tvz.spotlist.spotlistapp.security.JwtAuthenticationFilter;
import hr.tvz.spotlist.spotlistapp.security.JwtTokenProvider;
import hr.tvz.spotlist.spotlistapp.security.UserDetailsServiceImpl;
import hr.tvz.spotlist.spotlistapp.service.PlaylistService;
import hr.tvz.spotlist.spotlistapp.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

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


    private ReviewDTO testReviewDTO() {
        return ReviewDTO.builder()
                .id(1L)
                .playlistId(1L)
                .playlistTitle("Super")
                .rating(5)
                .comment("Very nice")
                .reviewedAt(LocalDateTime.now())
                .build();
    }

  /*  @Test
    @WithMockUser
    void getByPlaylistId_returnsListOf200() throws Exception {
        when(reviewService.findByPlaylistId(1L)).thenReturn(List.of(testReviewDTO()));

        mockMvc.perform(get("/api/reviews/playlist/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].comment").value("Very nice"));
    }*/

/*
    @Test
    @WithMockUser
    void add_validPayload_returns201() throws Exception {
        when(reviewService.add(any())).thenReturn(testReviewDTO());

        String json = """
            {
              "playlistId": 1,
              "playlistTitle": "D&b",
              "rating": 5,
              "comment": "Very nice"
            }
            """;

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment").value("Very nice"))
                .andExpect(jsonPath("$.playlistTitle").value("Very nice"))
                .andExpect(jsonPath("$.rating").value(5));
    }


    @Test
    @WithMockUser
    void delete_existingReview_returns204() throws Exception {
        when(reviewService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/reviews/1"))
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser
    void delete_nonExistentReview_returns404() throws Exception {
        when(reviewService.delete(5L)).thenReturn(false);

        mockMvc.perform(delete("/api/reviews/5"))
                .andExpect(status().isNotFound());
    }*/


}