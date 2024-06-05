import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import SpaceEditPage from '../pages/SpaceEditPage';
import '@testing-library/jest-dom/extend-expect';
import axios from 'axios';

// Mock useParams
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useParams: () => ({ spaceId: '1' }),
    useNavigate: jest.fn(),
}));

// Mock axios
jest.mock('axios');

describe('SpaceEditPage', () => {
    beforeEach(() => {
        axios.get.mockImplementation((url) => {
            if (url.includes('/api/space/1')) {
                return Promise.resolve({
                    data: {
                        spaceName: 'Test Space',
                        spaceLocation: 'Test Location',
                        spaceSize: 100,
                        spacePrice: 1000,
                        spaceDescription: 'Test Description',
                        availability: 'NOT_RELEASED',
                    },
                });
            }
            if (url.includes('/api/space/1/images')) {
                return Promise.resolve({
                    data: { url: 'http://example.com/image.jpg', id: 'imageId' },
                });
            }
        });
    });

    it('renders the SpaceEditPage component correctly', async () => {
        render(
            <Router>
                <SpaceEditPage />
            </Router>
        );

        // Wait for the space name to be rendered to ensure data has been loaded
        await waitFor(() => expect(screen.getByDisplayValue('Test Space')).toBeInTheDocument());

        // Check if the form fields are rendered
        expect(screen.getByLabelText('Space Name')).toBeInTheDocument();
        expect(screen.getByLabelText('Space Location')).toBeInTheDocument();
        expect(screen.getByLabelText('Space Size in m²')).toBeInTheDocument();
        expect(screen.getByLabelText('Space Price in $')).toBeInTheDocument();
        expect(screen.getByLabelText('Space Description')).toBeInTheDocument();

        // Check if the availability dropdown is rendered and has correct default value
        expect(screen.getByLabelText('Change Availability')).toBeInTheDocument();
        expect(screen.getByDisplayValue('Not released')).toBeInTheDocument();

        // Check if the submit button is rendered
        expect(screen.getByText('Save Changes')).toBeInTheDocument();

        // Check if the upload image section is rendered
        expect(screen.getByText('Upload Image')).toBeInTheDocument();
    });
});
