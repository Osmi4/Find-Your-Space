import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Navbar, NavbarContent, Button, Input , Dropdown, DropdownItem, DropdownMenu, DropdownTrigger} from "@nextui-org/react";

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
            <NavbarContent className="lg:flex" justify="between">
            <Dropdown className='lg:hidden'>
                    <DropdownTrigger className='lg:hidden'>
                        <div className='flex flex-col gap-y-[3px]'>
                            <hr className='w-[20px] border-black border-1'></hr>
                            <hr className='w-[20px] border-black border-1'></hr>
                            <hr className='w-[20px] border-black border-1'></hr>
                        </div>
                    </DropdownTrigger>

                    <DropdownMenu aria-label="Static Actions">
                        <DropdownItem key="home">
                            <Link to="/" className='font-bold'>Home</Link>
                        </DropdownItem>
                        <DropdownItem key="reports">
                            <Link to="/reports">Reports</Link>
                        </DropdownItem>
                        <DropdownItem key="reviews">
                            <Link to="/reviews">Reviews</Link>
                        </DropdownItem>
                        <DropdownItem key="users">
                            <Link to="/users">Users</Link>
                        </DropdownItem>
                        <DropdownItem key="spaces">
                            <Link to="/spaces">Spaces</Link>
                        </DropdownItem>
                        <DropdownItem key="bookings">
                            <Link to="/bookings">Bookings</Link>
                        </DropdownItem>
                        <DropdownItem key="message">
                            <Link to="/message">Messages</Link>
                        </DropdownItem>

                    </DropdownMenu>
                </Dropdown>
                <div className='gap-[1vw] hidden lg:flex'>
                    <Link to="/" className='font-bold'>Home</Link>
                    <Link to="/reports">Reports</Link>
                    <Link to="/reviews">Reviews</Link>
                    <Link to="/users">Users</Link>
                    <Link to="/spaces">Spaces</Link>
                    <Link to="/bookings">Bookings</Link>
                    <Link to="/message">Messages</Link>
                </div>
                
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
