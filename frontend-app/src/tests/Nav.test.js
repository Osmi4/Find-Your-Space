import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import { Auth0Provider, useAuth0 } from '@auth0/auth0-react';
import Nav from '../components/Nav';

jest.mock('../images/logo.png', () => 'logo.png');
jest.mock('@auth0/auth0-react');

const customRender = (ui, { providerProps, ...renderOptions } = {}) => {
    return render(
        <BrowserRouter>
            <Auth0Provider {...providerProps}>{ui}</Auth0Provider>
        </BrowserRouter>,
        renderOptions
    );
};

describe('Nav component', () => {
    beforeEach(() => {
        useAuth0.mockReturnValue({
            isAuthenticated: false,
            user: null,
            loginWithRedirect: jest.fn(),
            getIdTokenClaims: jest.fn(),
        });
    });

    afterEach(() => {
        jest.clearAllMocks();
    });

    test('renders Navbar with logo', () => {
        customRender(<Nav />);
        expect(screen.getByAltText('logo')).toBeInTheDocument();
    });

    test('renders search input', () => {
        customRender(<Nav />);
        expect(screen.getByPlaceholderText(/search/i)).toBeInTheDocument();
    });

    test('renders login button', () => {
        customRender(<Nav />);
        expect(screen.getByText(/login/i)).toBeInTheDocument();
    });
});
