import {Input} from "@nextui-org/react";
import {MailIcon} from '../icons/MailIcon.jsx';
import {LockIcon} from '../icons/LockIcon.jsx';
import {Checkbox} from "@nextui-org/react";
import {Link} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {useState, useEffect} from "react";
import {useAuth0} from '@auth0/auth0-react';

const LoginForm = () => {
    const { loginWithRedirect, getIdTokenClaims, isAuthenticated } = useAuth0();
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });
    const [token, setToken] = useState("");

    const handleChange = (e) => {
        const {name, value} = e.target;
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

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Perform login
            await loginWithRedirect({
                email: formData.email,
                password: formData.password,
            });

            // Token handling is done in the useEffect hook
            alert("Login successful!");
        } catch (error) {
            console.error("There was an error logging in!", error);
            alert("Login failed!");
        }
    };

    return (
        <div className="wrapper">
            <form onSubmit={handleSubmit}>
                <h1>Login</h1>
                <div className="flex flex-col gap-4 mt-[20px] mb-[5px]">
                    <Input
                        label="Email"
                        name="email"
                        placeholder="Enter your email"
                        variant="bordered"
                        endContent={
                            <MailIcon className="text-2xl text-default-200 pointer-events-none flex-shrink-0"/>
                        }
                        onChange={handleChange}
                    />
                    <Input
                        label="Password"
                        name="password"
                        placeholder="Enter your password"
                        variant="bordered"
                        endContent={
                            <LockIcon className="text-2xl text-default-200 pointer-events-none flex-shrink-0"/>
                        }
                        onChange={handleChange}
                    />
                </div>

                <div className="flex py-2 px-1 justify-between mb-[5px]">
                    <Checkbox
                        classNames={{
                            label: "text-small",
                        }}
                    >
                        Remember me
                    </Checkbox>
                    <Link color="primary" href="#" size="sm">
                        Forgot password?
                    </Link>
                </div>
                <Button color="primary" variant="bordered" type="submit"
                        className="bg-black w-[100%] h-[45px] text-[16px] font-semibold">
                    Log In
                </Button>
            </form>
        </div>
    )
}

export default LoginForm;
