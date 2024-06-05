import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import CheckoutPage from '../pages/CheckoutPage';
import '@testing-library/jest-dom';

// Mock dependencies
jest.mock('axios');
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),  // Maintain functionality for other hooks/components
    useParams: jest.fn(),
    useNavigate: jest.fn(),
}));

describe('CheckoutPage', () => {
    beforeEach(() => {
        useParams.mockReturnValue({ id: '1', startDate: '2024-01-01', endDate: '2024-01-05' });
        useNavigate.mockReturnValue(jest.fn());
        axios.get.mockResolvedValue({
            data: { spaceName: 'Space X', spacePrice: 100 }
        });
    });

    it('renders the total price correctly after data load', async () => {
        render(<CheckoutPage />);

        await waitFor(() => {
            // Use a function to match parts of the text split across different elements
            const totalPriceText = screen.getByText((content, node) => {
                const hasText = (node) => node.textContent === "$400";
                const nodeHasText = hasText(node);
                const childrenDontHaveText = Array.from(node.children).every(child => !hasText(child));

                return nodeHasText && childrenDontHaveText;
            });

            expect(totalPriceText).toBeTruthy();
        });
    });
});
