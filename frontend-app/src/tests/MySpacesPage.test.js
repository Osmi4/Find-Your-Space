import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import MySpacesPage from '../pages/MySpacesPage';
import '@testing-library/jest-dom';

describe('MySpacesPage', () => {
    it('renders the Filters heading', () => {
        render(
            <Router>
                <MySpacesPage />
            </Router>
        );

        // Check if the "Filters" heading is in the document
        expect(screen.getByText('Filters')).toBeTruthy();
    });
});
