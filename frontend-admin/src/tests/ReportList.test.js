import React from 'react';
import { render, screen, waitFor, fireEvent, within } from '@testing-library/react';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';
import ReportList from '../pages/ReportList';
import '@testing-library/jest-dom';

// Mock the necessary modules
jest.mock('@auth0/auth0-react');
jest.mock('axios');

describe('ReportList component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

test('displays error message when an error occurs while fetching reports', async () => {
    // Mock useAuth0 hook
    useAuth0.mockReturnValue({
      isAuthenticated: true,
      loginWithRedirect: jest.fn(),
      getIdTokenClaims: jest.fn()
    });

    // Mock axios to reject the fetch reports request
    axios.post.mockRejectedValue(new Error('Failed to fetch reports'));

    render(<ReportList />);

    // Wait for the error message to be displayed
    const errorMessage = await waitFor(() => screen.getByText("Unfortunately, an error occurred while trying to load the list of reports. Please try again later."));

    expect(errorMessage).toBeInTheDocument();
  });
});