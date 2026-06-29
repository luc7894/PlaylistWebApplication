import { Link } from 'react-router-dom'
import usePlaylists from './usePlaylists'
import { getGenreImage } from './genreImage'
import './HomePage.css'

function HomePage() {
    const { data, loading, error } = usePlaylists();

    if (loading) return <p>Učitavam...</p>;
    if (error) return <p>Greška!</p>;

    return (
        <div className="container">
            <div className="hero">
                <h2>Dobrodošli u Spotlist! 🎵</h2>
              
                <Link to="/playlists">
                    <button className="btn-add">Upravljaj Playlistama →</button>
                </Link>
            </div>

            <h2>Sve Playlist-e</h2>
            <div className="card-grid">
                {data.map((playlist, index) => (
                    <div key={index} className="playlist-card">
                        <img
                            src={getGenreImage(playlist.genre)}
                            alt={playlist.genre}
                        />
                        <div className="card-info">
                            <h3>{playlist.title}</h3>
                            <p> {playlist.genre}</p>
                            <p> {playlist.mood}</p>
                            <p>{playlist.isPublic ? ' Javna' : ' Privatna'}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default HomePage;