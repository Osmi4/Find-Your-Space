import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import ReportPage from '../pages/ReportPage';
import '@testing-library/jest-dom/extend-expect';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';

// Mock the Auth0 hook
jest.mock('@auth0/auth0-react', () => ({
    useAuth0: () => ({
        isAuthenticated: true,
        loginWithRedirect: jest.fn(),
    }),
}));

// Mock useParams
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({ id: '1' }),
}));

// Mock axios
jest.mock('axios');

describe('ReportPage', () => {
    beforeEach(() => {
        // Mock the axios responses
        axios.get.mockResolvedValue({
            data: {
                spaceName: 'Test Space',
                spaceDescription: 'Test Description',
                owner: {
                    userId: '2',
                    firstName: 'John',
                    lastName: 'Doe',
                    email: 'john.doe@example.com',
                    contactInfo: '123456789',
                },
            },
        });
        axios.post.mockResolvedValue({
            data: {},
        });
    });

    it('renders the ReportPage component correctly', async () => {
        render(
            <Router>
                <ReportPage />
            </Router>
        );

        // Wait for the component to finish loading
        await waitFor(() => {
            // Check if the title is rendered
            expect(screen.getByText('Report Page')).toBeTruthy();
            // Check if the submit button is rendered
            expect(screen.getByText('Submit Report')).toBeTruthy();
        });
    });
});
