import api from './axiosConfig';

export const getReviewsByPlaylistId = async (playlistId: number) => {
    const res = await api.get(`/api/reviews/playlist/${playlistId}`);
    return res.data;
};

export const addReview = async (review: object) => {
    const res = await api.post('/api/reviews', review);
    return res.data;
};

export const updateReview = async (id: number, review: object) => {
    const res = await api.put(`/api/reviews/${id}`, review);
    return res.data;
};

export const deleteReview = async (id: number) => {
    const res = await api.delete(`/api/reviews/${id}`);
    return res.data;
};