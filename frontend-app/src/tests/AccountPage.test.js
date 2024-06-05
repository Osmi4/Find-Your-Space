import React from 'react';
import { render, screen } from '@testing-library/react';
import AccountPage from '../pages/AccountPage';
import axios from 'axios';
import '@testing-library/jest-dom';

// Mocking axios to prevent actual HTTP requests
jest.mock('axios');

describe('AccountPage', () => {
    it('renders initial static text', () => {
        // Setup mock response for axios
        axios.get.mockResolvedValue({ data: {} });

        render(<AccountPage />);

        // Check for static text like headings that are always present using .toBeTruthy()
        const heading = screen.getByText('Account Details');
        expect(heading).toBeTruthy();
    });
});
