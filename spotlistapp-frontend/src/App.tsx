
import PlaylistListComponent from './components/PlaylistListComponent'
import PlaylistDetailComponent from './components/PlaylistDetailComponent'
import HomePage from './HomePage'
import LoginPage from './auth/LoginPage'
import ProtectedRoute from './auth/ProtectedRoute'
import { useAuth } from './auth/AuthContext'
import { Routes, Route, Link, useNavigate } from 'react-router-dom'

function App() {

const { user, logout } = useAuth() 
    const navigate = useNavigate()


    const handleLogout = () => {  
        logout()
        navigate('/login')
    }

    return (
        <div>
            <h1>Spotlist App</h1>
            <nav>
                <Link to="/">Početna</Link> |{" "}
                <Link to="/playlists">Lista Playlist-a</Link>
                {user && (
                    <>
                        {" "}| <span>👤 {user}</span>
                        {" "}
                        <button onClick={handleLogout}>Odjava</button>
                    </>
                )}
            </nav>

            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/login" element={<LoginPage />} />
                
               
                <Route path="/playlists" element={
                    

                    <ProtectedRoute>
                        <PlaylistListComponent />
                    </ProtectedRoute>
                } />
                <Route path="/details/:id" element={
                    <ProtectedRoute>
                        <PlaylistDetailComponent />
                    </ProtectedRoute>
                } />
            </Routes>
        </div>
    );
}

export default App;