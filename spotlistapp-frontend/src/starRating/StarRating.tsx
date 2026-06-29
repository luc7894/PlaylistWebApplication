import { useState } from 'react'

interface StarRatingProps {
    value: number;
    onChange: (rating: number) => void;
}

function StarRating({ value, onChange }: StarRatingProps) {
    const [hover, setHover] = useState<number | null>(null);

    return (
        <div style={{ display: 'flex', gap: '4px', cursor: 'pointer' }}>
            {[1, 2, 3, 4, 5].map(star => (
                <span
                    key={star}
                    style={{
                        fontSize: '2rem',
                        color: star <= (hover ?? value) ? '#f5a623' : '#ccc',
                        transition: 'color 0.2s'
                    }}
                    onClick={() => onChange(star)}
                    onMouseEnter={() => setHover(star)}
                    onMouseLeave={() => setHover(null)}
                >
                    ★
                </span>
            ))}
        </div>
    );
}

export default StarRating;