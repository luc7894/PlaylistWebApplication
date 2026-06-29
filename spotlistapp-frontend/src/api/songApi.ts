import api from './axiosConfig';

export const getSongsByPlaylistId = async (playlistId: number) => {
    const res = await api.get(`/api/songs/playlist/${playlistId}`);
    return res.data;
};