import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import BookingPage from '../pages/BookingPage';
import '@testing-library/jest-dom';

// Mock dependencies
jest.mock('axios');
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'), // This line is important to keep other hooks and components from react-router-dom working normally
    useParams: jest.fn(),
    useNavigate: jest.fn(),
}));

describe('BookingPage', () => {
    beforeEach(() => {
        // Setup mock returns for hooks with default values
        useParams.mockReturnValue({ bookingId: '123' });  // Correctly returning an object with a `bookingId`
        useNavigate.mockReturnValue(jest.fn());
    });

    it('renders static text after data load', async () => {
        // Mock axios to resolve with dummy data for booking, space, and owner details
        axios.get.mockResolvedValueOnce({
            data: {
                bookingId: '123',
                startDateTime: new Date(),
                endDateTime: new Date(),
                description: 'Sample Booking',
                status: 'ACCEPTED',
                cost: 100,
                dateAdded: new Date(),
                dateUpdated: new Date(),
            },
        });
        axios.get.mockResolvedValueOnce({
            data: { spaceId: '321', owner: { userId: 'user123' }, spaceName: 'Sample Space' },
        });
        axios.get.mockResolvedValueOnce({
            data: { firstName: 'John', lastName: 'Doe', email: 'john@example.com', contactInfo: '1234567890' },
        });

        render(<BookingPage />);

        await waitFor(() => {
            // Use `.toBeTruthy()` to assert that these elements are present
            expect(screen.getByText(/Booking Details/i)).toBeTruthy();
            expect(screen.getByText(/Space Details/i)).toBeTruthy();
            expect(screen.getByText(/Owner Details/i)).toBeTruthy();
        });
    });
});
