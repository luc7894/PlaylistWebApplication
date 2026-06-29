import api from '../api/axiosConfig';

const API_URL = '/api/playlists';

export const getAllPlaylists = async () => {
    const res = await api.get(API_URL);
    return res.data;
};

export const addPlaylist = async (playlist: object) => {
    const res = await api.post(API_URL, playlist);
    return res.data;
};

export const updatePlaylist = async (id: number, playlist: object) => {
    const res = await api.put(`${API_URL}/${id}`, playlist);
    return res.data;
};

export const deletePlaylist = async (id: number) => {
    const res = await api.delete(`${API_URL}/${id}`);
    return res.data;
};