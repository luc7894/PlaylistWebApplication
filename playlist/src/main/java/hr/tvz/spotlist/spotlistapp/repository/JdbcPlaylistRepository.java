package hr.tvz.spotlist.spotlistapp.repository;

import hr.tvz.spotlist.spotlistapp.model.Playlist;
import hr.tvz.spotlist.spotlistapp.model.PlaylistCommand;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Primary
@Repository
public class JdbcPlaylistRepository implements PlaylistRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcPlaylistRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("playlist")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Playlist> playlistRowMapper = (rs, rowNum) ->
            Playlist.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .genre(rs.getString("genre"))
                    .mood(rs.getString("mood"))
                    .isPublic(rs.getBoolean("is_public"))
                    .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                    .reviews(new ArrayList<>()) // ← dodaj praznu listu
                    .build();

    @Override
    public List<Playlist> findAll() {
        return jdbcTemplate.query("SELECT * FROM playlist", playlistRowMapper);
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return jdbcTemplate.query(
                        "SELECT * FROM playlist WHERE id = ?",
                        playlistRowMapper, id)
                .stream().findFirst();
    }



    @Override
    public Playlist add(Playlist playlist) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", playlist.getTitle());
        params.put("description", playlist.getDescription());
        params.put("genre", playlist.getGenre());
        params.put("mood", playlist.getMood());
        params.put("is_public", playlist.getIsPublic());
        params.put("created_at", LocalDateTime.now());

        Number id = simpleJdbcInsert.executeAndReturnKey(params);
        playlist.setId(id.longValue());
        return playlist;
    }

    @Override
    public boolean duplicateCheckByTitle(String title) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM playlist WHERE title = ?",
                Integer.class, title);
        return count != null && count > 0;
    }

    @Override
    public Optional<Playlist> update(Long id, PlaylistCommand command) {
        int rows = jdbcTemplate.update(
                "UPDATE playlist SET title=?, description=?, genre=?, mood=?, is_public=? WHERE id=?",
                command.getTitle(),
                command.getDescription(),
                command.getGenre(),
                command.getMood(),
                command.getIsPublic(),
                id
        );

        if (rows > 0) {
            return findById(id);
        }

        return Optional.empty();
    }


    //delete s id

    @Override
    public boolean deleteById(Long id) {
        int rows = jdbcTemplate.update(
                "DELETE FROM playlist WHERE id = ?", id);
        return rows > 0;
    }
}