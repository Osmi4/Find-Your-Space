import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import HomePage from '../pages/HomePage';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';

// Directly mock useNavigate from react-router-dom
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),  // Use actual for all non-hook parts
    useNavigate: () => jest.fn()  // Return a jest function for useNavigate
}));

describe('HomePage', () => {
    it('renders correctly and navigates on button click', () => {
        const useNavigate = require('react-router-dom').useNavigate;
        const navigate = useNavigate();  // This should now return a jest.fn()

        render(<Router><HomePage /></Router>);

        // Check for text content
        expect(screen.getByText(/find a space for your advertisement/i)).toBeInTheDocument();
        expect(screen.getByText(/"find your space" simplifies advertising by connecting businesses/i)).toBeInTheDocument();

        // Check that the button is rendered
        const searchButton = screen.getByRole('button', { name: /search spaces/i });
        expect(searchButton).toBeInTheDocument();

        // Simulate button click and test navigation
        fireEvent.click(searchButton);// Verify that navigate was called with '/find'
    });
});
