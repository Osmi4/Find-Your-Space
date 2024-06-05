import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import CompleteProfile from '../pages/CompleteProfile';
import '@testing-library/jest-dom';

// Mocking dependencies
jest.mock('axios');
jest.mock('@auth0/auth0-react');
jest.mock('react-router-dom', () => ({
    useNavigate: jest.fn(),
}));

describe('CompleteProfile', () => {
    // Setup common properties for each test
    beforeEach(() => {
        useAuth0.mockReturnValue({
            user: { sub: 'auth0|123456789' },
            loginWithRedirect: jest.fn(),
        });
        useNavigate.mockReturnValue(jest.fn());
        axios.get.mockResolvedValue({
            data: {
                userId: '1',
                bankAccountNumber: '1234567890',
                contactInfo: '123-456-7890',
                firstName: 'John',
                lastName: 'Doe'
            }
        });
    });


    it('renders the form with pre-filled values after data load', async () => {
        render(<CompleteProfile />);

        await waitFor(() => {
            expect(screen.getByLabelText('Bank Account')).toBeTruthy();
            expect(screen.getByLabelText('Phone number')).toBeTruthy();
            expect(screen.getByPlaceholderText('Please enter your bank account number').value).toBe('1234567890');
            expect(screen.getByPlaceholderText('Please enter your phone number').value).toBe('123-456-7890');
        });
    });
});
