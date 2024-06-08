import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Navbar, NavbarContent, Button, Input , Dropdown, DropdownItem, DropdownMenu, DropdownTrigger, Avatar, NavbarBrand} from "@nextui-org/react";
import logo from "../images/logo.png";
import axios from "axios";

const Nav = () => {
    const { loginWithRedirect, getIdTokenClaims, isAuthenticated, logout, user } = useAuth0();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [token, setToken] = useState("");
    const [isAdmin, setIsAdmin] = useState(false);

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
                const role = await axios.get(`http://localhost:8080/api/user/my-role`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                console.log(role.data)
                setIsAdmin(role.data === 'ADMIN');
            } catch (error) {
                console.error("Error fetching access token:", error);
            }
        };

        if (isAuthenticated) {
            fetchToken();
        }
    }, [isAuthenticated, getIdTokenClaims, token]);

    const handleLogout = () => {
        logout({ returnTo: window.location.origin });
    };

    return (
        <Navbar isBordered className='border-black' maxWidth="full">
            <NavbarContent className="lg:flex lg:justify-center">
                <Dropdown className='lg:hidden'>
                    <DropdownTrigger className='lg:hidden'>
                        <div className='flex flex-col gap-y-[3px] lg:hidden'>
                            <hr className='w-[20px] border-black border-1'></hr>
                            <hr className='w-[20px] border-black border-1'></hr>
                            <hr className='w-[20px] border-black border-1'></hr>
                        </div>
                    </DropdownTrigger>

                    <DropdownMenu aria-label="Static Actions">
                        {isAdmin && (
                            <DropdownItem key="admin">
                                <Link to="/admin">Admin</Link>
                            </DropdownItem>
                        )}
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
                <div className='hidden lg:flex gap-8 items-center'>
                <NavbarBrand className='lg:mr-[20px] hidden lg:block'>
                    <Link to="/">
                        <img src={logo} className="w-[48px] h-[48px]" alt="" />
                    </Link>
                </NavbarBrand>
                    {isAdmin && <Link to="/admin">Admin</Link>}
                    <Link to="/" className='font-bold'>Home</Link>
                    <Link to="/reports">Reports</Link>
                    <Link to="/reviews">Reviews</Link>
                    <Link to="/users">Users</Link>
                    <Link to="/spaces">Spaces</Link>
                    <Link to="/bookings">Bookings</Link>
                    <Link to="/message">Messages</Link>
                </div>
                <NavbarContent justify='end'>
                {!isAuthenticated && (
                    <Button onClick={loginWithRedirect}>Login</Button>
                )}
                </NavbarContent>
            </NavbarContent>
            {isAuthenticated && (
                <Dropdown>
                <DropdownTrigger>
                    <Avatar src={user.picture} as="button"/>
                </DropdownTrigger>
                <DropdownMenu>
                    <DropdownItem key="logout" color="error" onClick={handleLogout}>
                        Logout
                    </DropdownItem>
                </DropdownMenu>
                </Dropdown>
            )}
        </Navbar>
    );
}

export default Nav;
