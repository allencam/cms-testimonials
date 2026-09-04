import { useEffect, useState } from 'react';
import type {Testimonial} from "../types.ts";

export default function TestimonialList() {
    const [testimonials, setTestimonials] = useState<Testimonial[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Assuming your Spring Boot backend runs on the default port 8080
        const fetchTestimonials = async () => {
            try {
                const response = await fetch('http://localhost:8080/testimonials');
                if (!response.ok) {
                    throw new Error('Failed to fetch testimonials');
                }
                const data = await response.json();
                setTestimonials(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'Unknown error occurred');
            } finally {
                setLoading(false);
            }
        };

        fetchTestimonials();
    }, []);

    if (loading) return <p>Loading patient reviews...</p>;
    if (error) return <p>Error: {error}</p>;
    if (testimonials.length === 0) return <p>No reviews yet. Be the first!</p>;

    return (
        <div className="testimonial-container" style={{ display: 'flex', gap: '20px', flexWrap: 'wrap' }}>
            {testimonials.map((t) => (
                <div key={t.testimonialId} style={{ border: '1px solid #ccc', padding: '16px', borderRadius: '8px', maxWidth: '300px' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <h3 style={{ margin: 0 }}>{t.displayName}</h3>
                        <span style={{ color: '#fbbf24' }}>{'★'.repeat(t.rating)}</span>
                    </div>
                    <p style={{ fontSize: '0.9em', color: '#666' }}>Visited: {t.dateOfService}</p>
                    <p>"{t.message}"</p>
                </div>
            ))}
        </div>
    );
}