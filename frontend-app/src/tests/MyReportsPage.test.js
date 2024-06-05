import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import MyReportsPage from '../pages/MyReportsPage';
import '@testing-library/jest-dom';

describe('MyReportsPage', () => {
    it('renders My Reports heading', () => {
        render(
            <Router>
                <MyReportsPage />
            </Router>
        );

        // Check if the heading is in the document
        expect(screen.getByText('Please log in to view your reports.')).toBeTruthy();
    });
});
