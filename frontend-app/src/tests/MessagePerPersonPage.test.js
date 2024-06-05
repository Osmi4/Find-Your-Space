import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import MessagePersonPage from '../pages/MessagePersonPage';
import '@testing-library/jest-dom';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';

// Mock the useAuth0 hook
jest.mock('@auth0/auth0-react', () => ({
    useAuth0: () => ({
        isAuthenticated: true,
    }),
}));

// Mock the useParams hook to return a specific ID
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({
        id: '1',
    }),
}));

// Mock axios
jest.mock('axios');

describe('MessagePersonPage', () => {
    it('renders recipient information', async () => {
        // Mock axios responses
        axios.get
            .mockResolvedValueOnce({
                data: {
                    content: [],
                },
            })
            .mockResolvedValueOnce({
                data: {
                    firstName: 'Jane',
                    lastName: 'Doe',
                    email: 'jane.doe@example.com',
                    contactInfo: '123456789',
                },
            })
            .mockResolvedValueOnce({
                data: {
                    userId: 1,
                    firstName: 'Current',
                    lastName: 'User',
                    email: 'current.user@example.com',
                },
            });

        render(
            <Router>
                <MessagePersonPage />
            </Router>
        );

        await waitFor(() => {
            expect(screen.getByText('Jane Doe')).toBeTruthy();
            expect(screen.getByText('jane.doe@example.com')).toBeTruthy();
            expect(screen.getByText('123456789')).toBeTruthy();
        });
    });
});
