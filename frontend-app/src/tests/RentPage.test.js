import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import RentPage from '../pages/RentPage';
import '@testing-library/jest-dom/extend-expect';
import { useAuth0 } from '@auth0/auth0-react';

// Mock the Auth0 hook
jest.mock('@auth0/auth0-react', () => ({
    useAuth0: () => ({
        isAuthenticated: true,
        loginWithRedirect: jest.fn(),
    }),
}));

describe('RentPage', () => {
    it('renders the RentPage component correctly', () => {
        render(
            <Router>
                <RentPage />
            </Router>
        );

        // Check if the title is rendered
        expect(screen.getByText('Rent your space')).toBeTruthy();

        // Check if the submit button is rendered
        expect(screen.getByText('Submit Space Information')).toBeTruthy();
    });
});
