import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { Avatar, Dropdown, DropdownItem, DropdownMenu, DropdownTrigger } from "@nextui-org/react";

const UserIcon = () => {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setIsLoggedIn(true);
        }
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        navigate('/login');
    };

    return (
        <Dropdown>
            <DropdownTrigger>
                <Avatar color="primary" as="button">
                    Icon
                </Avatar>
            </DropdownTrigger>
            <DropdownMenu>
                {isLoggedIn ? (
                    <DropdownItem key="profile" onClick={() => navigate('/account')}>
                        Account Details
                    </DropdownItem>
                ) : (
                    <DropdownItem key="login" onClick={() => navigate('/login')}>
                        Login
                    </DropdownItem>
                )}
                {isLoggedIn && (
                    <DropdownItem key="logout" color="error" onClick={handleLogout}>
                        Logout
                    </DropdownItem>
                )}
                {isLoggedIn && (
                    <DropdownItem key="My spaces for rent" onClick={() => navigate('/my_spaces')}>
                        My spaces for rent
                    </DropdownItem>
                )}
            </DropdownMenu>
        </Dropdown>
    );
};

export default UserIcon;
