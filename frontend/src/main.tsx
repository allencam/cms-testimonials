import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import TestimonialList from "./components/TestimonalList.tsx";
import './style.css';

function App() {
    return (
        <main style={{ padding: '40px', fontFamily: 'sans-serif' }}>
            <h1>Patient Testimonials</h1>
            <TestimonialList />
        </main>
    );
}

// This is the crucial part that attaches React to your index.html
createRoot(document.getElementById('app')!).render(
    <StrictMode>
        <App />
    </StrictMode>,
);