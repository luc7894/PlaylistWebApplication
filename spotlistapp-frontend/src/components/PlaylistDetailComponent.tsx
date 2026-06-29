import { useParams, useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import usePlaylists from './../usePlaylists'
import { getReviewsByPlaylistId, addReview, updateReview, deleteReview } from './../api/reviewApi'
import StarRating from './../starRating/StarRating'
import { getSongsByPlaylistId } from './../api/songApi'
import { useAuth } from './../auth/AuthContext'

interface Review {
    id: number;
    playlistId: number;
    rating: number;
    comment: string;
    reviewedAt: string;
}

interface Song {
    id: number;
    playlistId: number;
    durationSeconds: number;
    name: string;
    album: string;
    artist: string;
}

function PlaylistDetailComponent() {
    const { id } = useParams();
    const { data, loading, error } = usePlaylists();
    const navigate = useNavigate();

    const [reviews, setReviews] = useState<Review[]>([]);
    const [reviewsLoading, setReviewsLoading] = useState(true);

    const [form, setForm] = useState({ rating: 1, comment: '' });

    const [editId, setEditId] = useState<number | null>(null);
    const [editForm, setEditForm] = useState({ rating: 1, comment: '' });

    const playlist = data[Number(id)];

    const [songs, setSongs] = useState<Song[]>([]);
    const [songsLoading, setSongsLoading] = useState(true);

    const { role } = useAuth();
    const canSeeContent = role === 'ROLE_ADMIN' || role === 'ROLE_USER';
    const isAdmin = role === 'ROLE_ADMIN';
    const canAddReview = role === 'ROLE_ADMIN' || role === 'ROLE_USER';

    const fetchSongs = async () => {
        if (playlist) {
            const data = await getSongsByPlaylistId(playlist.id);
            setSongs(data);
            setSongsLoading(false);
        }
    };

    useEffect(() => {
        fetchSongs();
    }, [playlist]);

    const fetchReviews = async () => {
        if (playlist) {
            const data = await getReviewsByPlaylistId(playlist.id);
            setReviews(data);
            setReviewsLoading(false);
        }
    };

    useEffect(() => {
        fetchReviews();
    }, [playlist]);

    const handleAddReview = async () => {
        try {
            await addReview({
                playlistId: playlist.id,
                rating: form.rating,
                comment: form.comment
            });
            await fetchReviews();
            setForm({ rating: 1, comment: '' });
        } catch (error) {
            alert('Greška pri dodavanju recenzije!');
        }
    };

    const handleUpdateReview = async (reviewId: number) => {
        try {
            await updateReview(reviewId, {
                playlistId: playlist.id,
                rating: editForm.rating,
                comment: editForm.comment
            });
            await fetchReviews();
            setEditId(null);
        } catch (error) {
            alert('Greška pri ažuriranju recenzije!');
        }
    };

    const handleDeleteReview = async (reviewId: number) => {
        try {
            await deleteReview(reviewId);
            setReviews(prev => prev.filter(r => r.id !== reviewId));
        } catch (error) {
            alert('Greška pri brisanju recenzije!');
        }
    };

    if (loading) return <p>Učitavam...</p>;
    if (error) return <p>Greška!</p>;
    if (!playlist) return <p>Playlist nije pronađen!</p>;

    return (
        <div className="container">
            <button onClick={() => navigate('/playlists')}>← Natrag</button>

            <h2>Detalji Playlista</h2>
            <p><strong>Naziv:</strong> {playlist.title}</p>
            <p><strong>Opis:</strong> {playlist.description}</p>
            <p><strong>Žanr:</strong> {playlist.genre}</p>
            <p><strong>Raspoloženje:</strong> {playlist.mood}</p>
            <p><strong>Javna:</strong> {playlist.isPublic ? 'Da' : 'Ne'}</p>

            {canSeeContent && (
                <>
                    <h2>Recenzije</h2>
                    {reviewsLoading ? (
                        <p>Učitavam recenzije...</p>
                    ) : reviews.length === 0 ? (
                        <p>Nema recenzija za ovu playlist.</p>
                    ) : (
                        <table>
                            <thead>
                                <tr>
                                    <th>Ocjena</th>
                                    <th>Komentar</th>
                                    <th>Datum</th>
                                    {isAdmin && <th>Akcija</th>}
                                </tr>
                            </thead>
                            <tbody>
                                {reviews.map(review => (
                                    <tr key={review.id}>
                                        <td>{'⭐'.repeat(review.rating)}</td>
                                        <td>{review.comment}</td>
                                        <td>{new Date(review.reviewedAt).toLocaleDateString()}</td>
                                        {isAdmin && (
                                            <td>
                                                <button className="btn-edit" onClick={() => {
                                                    setEditId(review.id);
                                                    setEditForm({ rating: review.rating, comment: review.comment });
                                                }}>Uredi</button>
                                                <button className="btn-delete" onClick={() =>
                                                    handleDeleteReview(review.id)
                                                }>Obriši</button>
                                            </td>
                                        )}
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}

                    {isAdmin && editId !== null && (
                        <div className="modal-overlay">
                            <div className="modal-content">
                                <h2>Uredi Recenziju</h2>
                                <div className="modal-fields">
                                    <div>
                                        <label>Ocjena: </label>
                                        <StarRating
                                            value={editForm.rating}
                                            onChange={(rating) => setEditForm({ ...editForm, rating })}
                                        />
                                    </div>
                                    <div>
                                        <label>Komentar: </label>
                                        <input
                                            value={editForm.comment}
                                            onChange={e => setEditForm({ ...editForm, comment: e.target.value })} />
                                    </div>
                                </div>
                                <div className="modal-buttons">
                                    <button className="btn-save"
                                        onClick={() => handleUpdateReview(editId)}>Spremi</button>
                                    <button className="btn-cancel"
                                        onClick={() => setEditId(null)}>Odustani</button>
                                </div>
                            </div>
                        </div>
                    )}

                    {canAddReview && (
                        <div className="add-form">
                            <h2>Dodaj recenziju</h2>
                            <label>Ocjena:</label>
                            <StarRating
                                value={form.rating}
                                onChange={(rating) => setForm({ ...form, rating })}
                            />
                            <input
                                placeholder="Komentar"
                                value={form.comment}
                                onChange={e => setForm({ ...form, comment: e.target.value })} />
                            <button className="btn-add" onClick={handleAddReview}>Dodaj</button>
                        </div>
                    )}

                    <h2>Pjesme</h2>
                    {songsLoading ? (
                        <p>Učitavam pjesme...</p>
                    ) : songs.length === 0 ? (
                        <p>Nema pjesama za ovu playlist.</p>
                    ) : (
                        <table>
                            <thead>
                                <tr>
                                    <th>Naziv</th>
                                    <th>Izvođač</th>
                                    <th>Album</th>
                                    <th>Trajanje</th>
                                </tr>
                            </thead>
                            <tbody>
                                {songs.map(song => (
                                    <tr key={song.id}>
                                        <td>{song.name}</td>
                                        <td>{song.artist}</td>
                                        <td>{song.album}</td>
                                        <td>{Math.floor(song.durationSeconds / 60)}:{String(Math.floor(song.durationSeconds) % 60).padStart(2, '0')}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    )}
                </>
            )}
        </div>
    );
}

export default PlaylistDetailComponent;