import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Avatar, Dropdown, DropdownItem, DropdownMenu, DropdownTrigger } from "@nextui-org/react";
import { useAuth0 } from '@auth0/auth0-react';

const UserIcon = ({user}) => {
    const navigate = useNavigate();
    const { logout, isAuthenticated } = useAuth0();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        if (isAuthenticated) {
            setIsLoggedIn(true);
        } else {
            setIsLoggedIn(false);
        }
    }, [isAuthenticated]);

    const handleLogout = () => {
        logout({ returnTo: window.location.origin });
    };

    return (
        <Dropdown>
            <DropdownTrigger>
                <Avatar src={user.picture} as="button"/>
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
                {isLoggedIn && (
                    <DropdownItem key="My messages" onClick={() => navigate('/message')}>
                        Messages
                    </DropdownItem>
                )}
                {isLoggedIn && (
                    <DropdownItem key="My reports" onClick={() => navigate('/my-reports')}>
                        My reports
                    </DropdownItem>
                )}
                {isLoggedIn && (
                    <DropdownItem key="My bookings" onClick={() => navigate('/my-bookings')}>
                        My bookings
                    </DropdownItem>
                )}
            </DropdownMenu>
        </Dropdown>
    );
};

export default UserIcon;
