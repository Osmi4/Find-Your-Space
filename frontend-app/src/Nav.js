import {Link} from 'react-router-dom';
import {Navbar, NavbarBrand, NavbarContent, NavbarItem,Link as Link2, Button, Input} from "@nextui-org/react";
import {SearchIcon} from "./SearchIcon.jsx";
import logo from "./images/logo.png";

const Nav=()=>{
    return (    
    <Navbar isBordered className='border-black justify-between' maxWidth="full">
      <NavbarContent className="hidden sm:flex gap-4" justify="center">
        <NavbarBrand className='mr-[50px]'>
          <Link to="/">
            <img src={logo} className="w-[48px] h-[48px] bg-center block mx-auto" alt=""/> 
          </Link>
        </NavbarBrand>
        <NavbarItem isActive>
          <Link to="/">
            Find your space
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link to="/find">
            Find Space
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link to="/rent">
            Rent Space
          </Link>
        </NavbarItem>
        <NavbarItem>
          <Link to="/about">
            About
          </Link>
        </NavbarItem>
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
        />
      </NavbarContent>
      <NavbarContent justify="end" className='ml-[200px] mr-[30px]'>
        <NavbarItem className="hidden lg:flex">
          <Link to="/login">Login</Link>
        </NavbarItem>
        <NavbarItem>
          <Button as={Link2} color="default" href="/signup">
            Sign Up
          </Button>
        </NavbarItem>
      </NavbarContent>
    </Navbar>
    )

}

export default Nav;