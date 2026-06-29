import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from './AuthContext'

import { authService } from './authService'

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleLogin = async () => {
    try {
        const token = await authService.login(username, password);
        login(token); 
        navigate('/playlists');
    } catch {
        setError('Pogrešno korisničko ime ili lozinka!');
    }
};

    return (
        <div className="login-container">
            <div className="login-box">
                <h2> Spotlist Login</h2>
                {error && <p className="error">{error}</p>}
                <div className="login-fields">
                    <label>Korisničko ime:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                        placeholder="Upiši korisničko ime"
                    />
                    <label>Lozinka:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        placeholder="Upiši lozinku"
                    />
                </div>
                <button className="btn-add" onClick={handleLogin}>
                    Prijava
                </button>
            </div>
        </div>
    );
}

export default LoginPage;