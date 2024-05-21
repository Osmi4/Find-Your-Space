import {Input} from "@nextui-org/react";
import {MailIcon} from './MailIcon.jsx';
import {LockIcon} from './LockIcon.jsx';
import {Checkbox} from "@nextui-org/react";
import {Link} from "@nextui-org/react";
import {Button} from "@nextui-org/react";
import {useState} from "react";
import axios from 'axios';

const LoginForm = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
    });
    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                email: formData.email,
                password: formData.password,
            });

            if (response.status === 200) {
                const token = response.data.token;
                localStorage.setItem('token', token);
                alert("Login successful!");
            }
        } catch (error) {
            console.error("There was an error logging in!", error);
            alert("Login failed!");
        }
    };
    return (
        <div class="wrapper">
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