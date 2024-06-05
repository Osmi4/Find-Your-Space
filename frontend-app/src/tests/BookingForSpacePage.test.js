import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import BookingsForSpacePage from '../pages/BookingsForSpacePage';
import '@testing-library/jest-dom';

// Mock dependencies
jest.mock('axios');
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),  // Keep other hooks and components
    useParams: jest.fn()
}));

describe('BookingsForSpacePage', () => {
    beforeEach(() => {
        // Setup mock returns for hooks
        useParams.mockReturnValue({ spaceId: '123' });  // Mock useParams return value
    });

    it('renders booking and space information after data load', async () => {
        // Mock axios to resolve with dummy data for bookings and space
        axios.get.mockResolvedValueOnce({
            data: {
                content: [{ bookingId: '1', startDateTime: '2024-01-01', endDateTime: '2024-01-02', description: 'Test Booking', status: 'ACCEPTED', cost: 200 }],
                totalPages: 1
            }
        });
        axios.get.mockResolvedValueOnce({
            data: { spaceId: '123', spaceName: 'Test Space', spaceDescription: 'Description of Test Space', spacePrice: 300 }
        });

        render(<BookingsForSpacePage />);

        // Wait for axios data to load and text to be available
        await waitFor(() => {
            expect(screen.getByText('Bookings for Space')).toBeTruthy();
            expect(screen.getByText('Test Space')).toBeTruthy();
            expect(screen.getByText(/Description of Test Space/)).toBeTruthy();
            expect(screen.getByText(/Test Booking/)).toBeTruthy();
        });
    });
});
