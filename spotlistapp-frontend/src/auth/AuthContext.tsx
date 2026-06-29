import { createContext, useContext, useState, type ReactNode } from 'react';
import { setAccessToken } from '../api/axiosConfig';

interface AuthContextType {
    user: string | null;
    token: string | null;
    role: string | null;
    login: (token: string) => void;
    logout: () => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
    const [token, setToken] = useState<string | null>(null);
    const [user, setUser] = useState<string | null>(null);
    const [role, setRole] = useState<string | null>(null);

    const login = (newToken: string) => {
    const payload = JSON.parse(atob(newToken.split('.')[1]));

    setToken(newToken);
    setAccessToken(newToken); 

    setUser(payload.sub);

    const extractedRole =
        payload.authorities
            ?.split(',')
            .find((a: string) => a.startsWith('ROLE_'))
        ?? null;

    setRole(extractedRole);
};

   const logout = () => {
    setToken(null);
    setUser(null);
    setRole(null);
    setAccessToken(null);
};

    return (
        <AuthContext.Provider value={{ user, token, role, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);
    if (!context) throw new Error('useAuth mora biti unutar AuthProvider!');
    return context;
}