import { useState, useEffect } from 'react';
import type { Playlist } from './Playlist';
import { getAllPlaylists } from './api/playlistApi';




const usePlaylists = () => {
    const [data, setData] = useState<Playlist[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
   

   const fetchData = async () => {
    setLoading(true);
    try {
        const data = await getAllPlaylists();
        setData(data);
        setLoading(false);
    } catch {
        setError("Greška pri dohvatu podataka");
        setLoading(false);
    }
};

    useEffect(() => {
        fetchData();
    }, []);

   return { data, setData, loading, error, refetch: fetchData };
};
export default usePlaylists;


  



/*
data je trenutno
setData se koristi da promijenimo(gfunkcija)
useState se poziva jednom i kaze da se promijeni neka varijabla i osvjezi na ekranu, pamti vrijednost

*/