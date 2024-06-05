import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import RegisterPage from '../pages/RegisterPage';
import '@testing-library/jest-dom';

// Mock the Auth0 hook
jest.mock('@auth0/auth0-react', () => ({
    useAuth0: () => ({
        loginWithRedirect: jest.fn(),
    }),
}));

describe('RegisterPage', () => {
    it('renders the New User heading and Register with Auth0 button', () => {
        render(
            <Router>
                <RegisterPage />
            </Router>
        );

        // Check if the "New User" heading is in the document
        expect(screen.getByText('New User')).toBeTruthy();

        // Check if the "Register with Auth0" button is in the document
        expect(screen.getByText('Register with Auth0')).toBeTruthy();
    });
});
