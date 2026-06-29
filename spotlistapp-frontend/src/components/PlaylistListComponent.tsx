import { useNavigate } from 'react-router-dom'
import { useState, useEffect } from 'react'
import {
    getAllPlaylists,
    addPlaylist,
    updatePlaylist,
    deletePlaylist,
} from './../api/playlistApi'
import type { Playlist } from './../Playlist'
import '.././Modal.css'
import { useAuth } from './../auth/AuthContext'  

function PlaylistListComponent() {
    const [data, setData] = useState<Playlist[]>([])
    const [loading, setLoading] = useState(true)
    const navigate = useNavigate()
    const { role } = useAuth()              
    const isAdmin = role === 'ROLE_ADMIN'   

    const [form, setForm] = useState({
        title: '',
        description: '',
        genre: '',
        mood: ''
    })

    const [editId, setEditId] = useState<number | null>(null)

    const [editForm, setEditForm] = useState({
        title: '',
        description: '',
        genre: '',
        mood: '',
        isPublic: true
    })

    const fetchData = async () => {
        const data = await getAllPlaylists()
        setData(data)
        setLoading(false)
    }

    useEffect(() => {
        fetchData()
    }, [])

    const handleSubmit = async () => {
    try {
        await addPlaylist({ ...form, isPublic: true })
        await fetchData()
        setForm({ title: '', description: '', genre: '', mood: '' })
    } catch (error) {
        alert('Greška pri dodavanju!')
        console.error(error)
    }
}

const handleUpdate = async (id: number) => {
    try {
        await updatePlaylist(id, {
            title: editForm.title,
            description: editForm.description,
            genre: editForm.genre,
            mood: editForm.mood,
            isPublic: editForm.isPublic
        })
        await fetchData()
        setEditId(null)
    } catch (error) {
        alert('Greška pri ažuriranju!')
        console.error(error)
    }
}

const handleDelete = async (id: number) => {
    setData(prev => prev.filter(p => p.id !== id))
    try {
        await deletePlaylist(id)
    } catch (error) {
        alert('Greška pri brisanju!')
        fetchData() // vrati stanje ako je failalo
    }
}

    if (loading) return <p>Učitavam...</p>

    return (
        <div className="container">
            <h2>Playlist Lista</h2>

            <table>
                <thead>
                    <tr>
                        <th>Naziv</th>
                        <th>Žanr</th>
                        <th>Raspoloženje</th>
                        {isAdmin && <th>Akcija</th>}
                    </tr>
                </thead>
                <tbody>
                    {data.map((playlist, index) => (
                        <tr key={index} onClick={() => navigate(`/details/${index}`)}>
                            <td>{playlist.title}</td>
                            <td>{playlist.genre}</td>
                            <td>{playlist.mood}</td>
                            {isAdmin && (
                                <td>
                                    <button className="btn-edit" onClick={(e) => {
                                        e.stopPropagation()
                                        setEditId(playlist.id)
                                        setEditForm({
                                            title: playlist.title,
                                            description: playlist.description,
                                            genre: playlist.genre,
                                            mood: playlist.mood,
                                            isPublic: playlist.isPublic
                                        })
                                    }}>Uredi</button>
                                    <button className="btn-delete" onClick={(e) => {
                                        e.stopPropagation()
                                        handleDelete(playlist.id)
                                    }}>Obriši</button>
                                </td>
                            )}
                        </tr>
                    ))}
                </tbody>
            </table>

            {isAdmin && editId !== null && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h2>Uredi Playlist</h2>
                        <div className="modal-fields">
                            <input value={editForm.title}
                                onChange={e => setEditForm({ ...editForm, title: e.target.value })}
                                placeholder="Naziv" />
                            <input value={editForm.description}
                                onChange={e => setEditForm({ ...editForm, description: e.target.value })}
                                placeholder="Opis" />
                            <input value={editForm.genre}
                                onChange={e => setEditForm({ ...editForm, genre: e.target.value })}
                                placeholder="Žanr" />
                            <input value={editForm.mood}
                                onChange={e => setEditForm({ ...editForm, mood: e.target.value })}
                                placeholder="Raspoloženje" />
                        </div>
                        <div className="modal-buttons">
                            <button className="btn-save"
                                onClick={() => editId && handleUpdate(editId)}>Spremi</button>
                            <button className="btn-cancel"
                                onClick={() => setEditId(null)}>Odustani</button>
                        </div>
                    </div>
                </div>
            )}

            {isAdmin && (
                <div className="add-form">
                    <h2>Dodaj novu Playlist</h2>
                    <input placeholder="Naziv" value={form.title}
                        onChange={e => setForm({ ...form, title: e.target.value })} />
                    <input placeholder="Opis" value={form.description}
                        onChange={e => setForm({ ...form, description: e.target.value })} />
                    <input placeholder="Žanr" value={form.genre}
                        onChange={e => setForm({ ...form, genre: e.target.value })} />
                    <input placeholder="Raspoloženje" value={form.mood}
                        onChange={e => setForm({ ...form, mood: e.target.value })} />
                    <button className="btn-add" onClick={handleSubmit}>Dodaj</button>
                </div>
            )}
        </div>
    )
}

export default PlaylistListComponent