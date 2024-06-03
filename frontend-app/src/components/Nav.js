import { Link, useNavigate } from 'react-router-dom';
import { Navbar, NavbarBrand, NavbarContent, NavbarItem, Button, Input, Dropdown, DropdownTrigger, DropdownMenu, DropdownItem } from "@nextui-org/react";
import { SearchIcon } from "../icons/SearchIcon.jsx";
import logo from "../images/logo.png";
import { useState, useEffect } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';
import spaces from '../spaces.js';
import UserIcon from "../pages/UserIcon";

const Nav = () => {
    const { loginWithRedirect, isAuthenticated, getIdTokenClaims, user } = useAuth0();
    const navigate = useNavigate();
    const [searchInput, setSearchInput] = useState("");
    const [filteredSpaces, setFilteredSpaces] = useState([]);

    const handleChange = (e) => {
        setSearchInput(e.target.value);
    };

    const pageChanged = () => {
        setSearchInput("");
    }

    useEffect(() => {
        const checkProfileCompletion = async () => {
            if (isAuthenticated) {
                const tokenClaims = await getIdTokenClaims();
                const token = tokenClaims.__raw;
                localStorage.setItem('authToken', token);

                try {
                    const responseConfirm = await axios.get('http://localhost:8080/api/user/confirm-login', {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    const response = await axios.get('http://localhost:8080/api/user/my-details', {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    if (!response.data.detailsConfigured) {
                        navigate('/complete-profile');
                    }
                } catch (error) {
                    console.error("Error checking profile completeness", error);
                }
            }
        };

        checkProfileCompletion();
    }, [isAuthenticated, getIdTokenClaims, navigate]);

    useEffect(() => {
        if (searchInput.length > 0) {
            setFilteredSpaces(spaces.filter((space) => space.title.toUpperCase().trim().includes(searchInput.trim().toUpperCase())));
        } else {
            setFilteredSpaces([]);
        }
    }, [searchInput]);

    return (
        <Navbar isBordered className='border-black justify-between' maxWidth="full">
            <NavbarContent className="lg:flex" justify="center">
                <NavbarBrand className='lg:mr-[50px] hidden lg:block'>
                    <Link to="/">
                        <img src={logo} className="w-[48px] h-[48px]" alt="" />
                    </Link>
                </NavbarBrand>
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
                            <Link to="/" className="hover:text-sky-600 font-bold">
                                Find your space
                            </Link>
                        </DropdownItem>
                        <DropdownItem key="find">
                            <Link to="/find" className="hover:text-sky-600">
                                Find Space
                            </Link>
                        </DropdownItem>
                        <DropdownItem key="rent">
                            <Link to="/rent" className="hover:text-sky-600">
                                Rent Space
                            </Link>
                        </DropdownItem>
                        <DropdownItem key="about">
                            <Link to="/about" className="hover:text-sky-600">
                                About
                            </Link>
                        </DropdownItem>
                    </DropdownMenu>
                </Dropdown>

                <div className='hidden lg:flex gap-8 items-center'>
                    <NavbarItem isActive>
                        <Link to="/" className="hover:text-sky-600 font-bold">
                            Find your space
                        </Link>
                    </NavbarItem>
                    <NavbarItem>
                        <Link to="/find" className="hover:text-sky-600">
                            Find Space
                        </Link>
                    </NavbarItem>
                    <NavbarItem>
                        <Link to="/rent" className="hover:text-sky-600">
                            Rent Space
                        </Link>
                    </NavbarItem>
                    <NavbarItem>
                        <Link to="/about" className="hover:text-sky-600">
                            About
                        </Link>
                    </NavbarItem>
                </div>
                <NavbarItem>
                    <Input
                        classNames={{
                            base: "max-w-full w-[150px] sm:w-[10rem] h-10 pt-[1px]",
                            mainWrapper: "h-full",
                            input: "text-small",
                            inputWrapper: "h-full font-normal text-default-500 bg-inherit dark:bg-default-500/20",
                        }}
                        placeholder="Search"
                        size="sm"
                        startContent={<SearchIcon size={16} />}
                        type="search"
                        onChange={handleChange}
                        value={searchInput}
                    />
                    {searchInput &&
                        (<ul
                            className="absolute bg-gray-100 rounded-lg mt-[10px] flex flex-col px-[20px] py-[10px]"
                            data-testid="search-results"
                        >
                            {filteredSpaces.map((space) => (
                                <li key={space.id}>
                                    <Link to={`/space/${space.id}`} onClick={pageChanged}>
                                        {space.title}
                                    </Link>
                                </li>
                            ))}
                        </ul>)}
                </NavbarItem>

            </NavbarContent>
            <NavbarContent justify="end" className='sm:ml-[200px] sm:mr-[30px]'>
                {!isAuthenticated ? (
                    <NavbarItem>
                        <Button onClick={() => loginWithRedirect()} color="default">
                            Login
                        </Button>
                    </NavbarItem>
                ) : (
                    <NavbarItem>
                        <UserIcon user={user} />
                    </NavbarItem>
                )}
            </NavbarContent>
        </Navbar>
    );
}

export default Nav;
