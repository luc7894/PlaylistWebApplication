



import playlist1 from './../images/playlist1.jpg'
import playlist2 from './../images/playlist2.jpg'
import playlist3 from './../images/playlist3.jpg'

export const getGenreImage = (genre: string): string => {
    const images: { [key: string]: string } = {
        'Electronic': playlist1,
        'Drum and bass': playlist1,
        'High energy': playlist1,
        'Rap': playlist2,
       
    };
    return images[genre] || playlist3;
};