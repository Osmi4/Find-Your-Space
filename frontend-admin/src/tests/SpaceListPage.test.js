import React from 'react';
import { render, screen, waitFor, fireEvent, within } from '@testing-library/react';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';
import SpaceListPage from '../pages/SpaceListPage';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';

// Mock the necessary modules
jest.mock('@auth0/auth0-react');
jest.mock('axios');

describe('SpaceListPage component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

test('displays error message when an error occurs while fetching space list', async () => {
    // Mock useAuth0 hook
    useAuth0.mockReturnValue({
      isAuthenticated: true,
      loginWithRedirect: jest.fn(),
      getIdTokenClaims: jest.fn()
    });

    // Mock axios to reject the fetch space list request
    axios.post.mockRejectedValue(new Error('Failed to fetch space list'));

    render(
        <Router>
            <SpaceListPage />
        </Router>
    );

    // Wait for the error message to be displayed
    const errorMessage = await waitFor(() => screen.getByText("Unfortunately, there was an error trying to load the list of spaces. Please try again later."));

    expect(errorMessage).toBeInTheDocument();
  });
});