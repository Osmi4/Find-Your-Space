import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import MyBookingsPage from '../pages/MyBookingsPage';
import '@testing-library/jest-dom';
import axios from 'axios';

// Mock axios
jest.mock('axios');

describe('MyBookingsPage', () => {
    it('renders the "My Bookings" heading', async () => {
        // Mock axios response
        axios.get.mockResolvedValue({
            data: {
                content: [],
                totalPages: 1,
            },
        });

        render(
            <Router>
                <MyBookingsPage />
            </Router>
        );

        await waitFor(() => {
            expect(screen.getByText('My Bookings')).toBeTruthy();
        });
    });
});
