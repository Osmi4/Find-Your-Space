import {Link} from 'react-router-dom';
import {Navbar, NavbarBrand, NavbarContent, NavbarItem,Link as Link2, Button, Input} from "@nextui-org/react";
import {SearchIcon} from "../icons/SearchIcon.jsx";
import logo from "../images/logo.png";
import { useState , useEffect} from 'react';
import spaces from '../spaces.js';

const Nav=()=>{
    const [searchInput, setSearchInput] = useState("");
    const [filteredSpaces, setFilteredSpaces] = useState([]);

    const handleChange = (e) => {
      setSearchInput(e.target.value);
    };

    const pageChanged = () => {
      setSearchInput("");
    }
    
    useEffect(() => {
      if (searchInput.length > 0) {
        setFilteredSpaces(spaces.filter((space) => space.title.toUpperCase().trim().includes(searchInput.trim().toUpperCase())));
      } else {
        setFilteredSpaces([]);
      }
    }, [searchInput]);

    return (    
    <Navbar isBordered className='border-black justify-between' maxWidth="full">
      <NavbarContent className="hidden sm:flex gap-8" justify="center">
        <NavbarBrand className='mr-[50px]'>
          <Link to="/">
            <img src={logo} className="w-[48px] h-[48px] bg-center block mx-auto" alt=""/> 
          </Link>
        </NavbarBrand>
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
        <NavbarItem>
        <Input
          classNames={{
            base: "max-w-full sm:max-w-[10rem] h-10 pt-[1px]",
            mainWrapper: "h-full",
            input: "text-small",
            inputWrapper: "h-full font-normal text-default-500 bg-inherit dark:bg-default-500/20",
          }}
          placeholder="Search"
          size="sm"
          startContent={<SearchIcon size={16} />}
          type="search"
          onChange={handleChange}
          value = {searchInput}
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
      <NavbarContent justify="end" className='ml-[200px] mr-[30px]'>
        <NavbarItem className="hidden lg:flex">
        <Button as={Link2} color="default" href="/login">
            Login
          </Button>
        </NavbarItem>
      </NavbarContent>
    </Navbar>
    )

}

export default Nav;