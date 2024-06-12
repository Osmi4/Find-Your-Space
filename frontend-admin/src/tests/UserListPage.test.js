import React from 'react';
import { render, screen, waitFor, fireEvent, within } from '@testing-library/react';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';
import UserListPage from '../pages/UserListPage';
import '@testing-library/jest-dom';
import { BrowserRouter as Router } from 'react-router-dom';

// Mock the necessary modules
jest.mock('@auth0/auth0-react');
jest.mock('axios');

describe('UserListPage component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

test('displays error message when an error occurs while fetching user list', async () => {
    // Mock useAuth0 hook
    useAuth0.mockReturnValue({
      isAuthenticated: true,
      loginWithRedirect: jest.fn(),
      getIdTokenClaims: jest.fn()
    });

    // Mock axios to reject the fetch user list request
    axios.post.mockRejectedValue(new Error('Failed to fetch user list'));

    render(
        <Router>
            <UserListPage />
        </Router>
    );

    // Wait for the error message to be displayed
    const errorMessage = await waitFor(() => screen.getByText("Unfortunately, there was an error loading the user list. Please try again later."));

    expect(errorMessage).toBeInTheDocument();
  });
});