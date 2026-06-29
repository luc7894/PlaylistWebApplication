import axios from 'axios';

let accessToken: string | null = null;

export const setAccessToken = (t: string | null) => {
  accessToken = t;
};

export const getAccessToken = () => accessToken;

const api = axios.create({
  baseURL: 'http://localhost:8080'
});

api.interceptors.request.use((config) => {
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
});

export default api;