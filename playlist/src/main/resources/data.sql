INSERT INTO playlist (title, description, genre, mood, is_public, created_at)
VALUES
  ('D&b', 'Drum and bass', 'Electronic', 'High energetic', true, NOW()),
  ('RADI BAZA?', 'High energy', 'Rap', 'Energetic', false, NOW()),
   ('Jazz Night', 'Smooth jazz', 'Jazz', 'Relaxed', true, NOW());



INSERT INTO review (playlist_id, rating, comment, reviewed_at)
VALUES
    (1, 5, 'Savrsena playlist!', NOW()),
    (1, 4, 'Dobar mix', NOW()),
    (2, 3, 'Solidno', NOW()),
    (2, 4, 'Dobro', NOW()),
    (3, 5, 'Odlicno!', NOW()),
    (3, 5, 'Predobro!', NOW()),
    (3, 4, 'Super', NOW());


INSERT INTO song (playlist_id, name, artist, album, duration_seconds) VALUES
 (1, 'Chase & Status - Blind Faith', 'Chase & Status', 'No More Idols', 245.50),
  (1, 'Pendulum - Watercolour', 'Pendulum', 'Immersion', 318.00),
  (2, 'Kendrick Lamar - HUMBLE.', 'Kendrick Lamar', 'DAMN.', 177.00),
 (3, 'Miles Davis - So What', 'Miles Davis', 'Kind of Blue', 562.00);

INSERT INTO authority (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER'),('ROLE_GUEST');

INSERT INTO app_user (username, password, first_name, last_name) VALUES
                                                                     ('admin', '$2a$10$tNY9VcJKqO1RGY/pcMItxu9Ai6MHqd8Yt0knbhAv2Qkz7QJfpasQG', 'Admin', 'User'),
                                                                     ('user', '$2a$10$tM4v9WjbwetVBwtjn6cb0ugLepUAeVSFdA9W64Oz5UBSbQBAKg.vi', 'Regular', 'User'),
                                                                     ('guest', '$2a$10$e0uLvAQWkjvBUr/ASW9l2uD.Z840KjX1iLNYLIZLDPKBF/PEKLGVm', 'Guest', 'User');

INSERT INTO user_authority (user_id, authority_id) VALUES
                                                       (1, 1),
                                                       (2, 2),
                                                        (3, 3);

