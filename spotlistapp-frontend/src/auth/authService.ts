import axiosInstance from '../api/axiosConfig'

export const authService = {
    
    
    login: async (username: string, password: string) => {
        const res = await axiosInstance.post('/api/auth/login', {
            username,
            password
        });
        return res.data.accessToken; 
    },

    
    isAuthenticated: (token: string | null): boolean => {
        return token !== null;
    }
};