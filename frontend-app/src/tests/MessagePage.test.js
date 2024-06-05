import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import MessagePage from '../pages/MessagePage';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';

// Mock the useAuth0 hook
jest.mock('@auth0/auth0-react', () => ({
    useAuth0: () => ({
        isAuthenticated: true,
    }),
}));

// Mock axios
jest.mock('axios');

describe('MessagePage', () => {
    it('renders loading spinner and messages', async () => {
        // Mock the axios response
        axios.get.mockResolvedValueOnce({
            data: {
                content: [
                    { senderId: 1, message: 'Hello' },
                ],
            },
        }).mockResolvedValueOnce({
            data: {
                firstName: 'John',
                lastName: 'Doe',
                email: 'john.doe@example.com',
            },
        });

        render(
            <Router>
                <MessagePage />
            </Router>
        );

        // Check that the loading spinner is initially present

        // Wait for the async operations to complete
        await waitFor(() => {
            expect(screen.getByText('John Doe')).toBeTruthy();
        });
    });
});
