import React from 'react';
import { render, screen } from '@testing-library/react';
import AboutPage from "../pages/AboutPage";
import '@testing-library/jest-dom';

describe('AboutPage', () => {
    it('renders the title "About Us"', () => {
        render(<AboutPage />);
        const titleElement = screen.getByText(/About Us/i);
        expect(titleElement).toBeTruthy();
    });
});
