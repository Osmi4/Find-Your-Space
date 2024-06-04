import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Navbar, NavbarContent, Button, Input } from "@nextui-org/react";

const Nav = () => {
    const { loginWithRedirect, getIdTokenClaims, isAuthenticated, logout } = useAuth0();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [token, setToken] = useState("");

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    useEffect(() => {
        const fetchToken = async () => {
            try {
                const accessToken = await getIdTokenClaims();
                setToken(accessToken.__raw);
                localStorage.setItem('authToken', accessToken.__raw);
            } catch (error) {
                console.error("Error fetching access token:", error);
            }
        };

        if (isAuthenticated) {
            fetchToken();
        }
    }, [isAuthenticated, getIdTokenClaims]);

    return (
        <Navbar isBordered className='border-black justify-between' maxWidth="full">
            <NavbarContent className="lg:flex" justify="center">
                <Link to="/">Home</Link>
                <Link to="/reports">Reports</Link>
                <Link to="/reviews">Reviews</Link>
                {isAuthenticated ? (
                    <Button onClick={() => logout({ returnTo: window.location.origin })}>Logout</Button>
                ) : (
                    <Button onClick={loginWithRedirect}>Login</Button>
                )}
            </NavbarContent>
            {isAuthenticated && (
                <div>You are Logged in</div>
            )}
        </Navbar>
    );
}

export default Nav;
