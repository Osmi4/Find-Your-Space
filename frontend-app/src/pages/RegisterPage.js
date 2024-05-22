import { credentials } from "../LoginForm";
import { useState } from "react";
import axios from "axios";

const RegisterPage = () => {
    const [formData , setFormData] = useState({
        password: "",
        email: "",
        firstName: "",
        lastName: "",
        confirmPassword: '',
    })

    const handleChange = (e) => {
        const{name, value , type, checked} = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: type === 'checkbox' ? checked : value,
        }));
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            alert("Passwords do not match");
            return;
        }
        try {
            const response = await axios.post('http://localhost:8080/api/auth/register', {
                email: formData.email,
                password: formData.password,
                firstName: formData.firstName,
                lastName: formData.lastName,
            });

            if (response.status === 200) {
                alert("Registration successful!");
            }
        } catch (error) {
            console.error("There was an error registering!", error);
        }
    };

    return (
        <div className="mt-[150px]">   
            <form className="flex flex-col gap-[10px] items-center border-2 mx-[750px] py-[20px] border-black rounded-3xl bg-slate-100">
                <h1 className="text-center mb-[30px] text-3xl font-semibold underline">New user</h1>
                {/*<input type="text" placeholder="Username" defaultValue={credentials.username} className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>*/}
                <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="First Name"
                    className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>
                <input
                    type="text"
                    name="lastName"
                    placeholder="Last Name"
                    value={formData.lastName}
                    onChange={handleChange}
                    className="border-2 rounded-3xl px-[10px] py-[8px] border-black"
                />
                <input type="email"
                       name="email"
                       placeholder="Email"
                       value={formData.email}
                       onChange={handleChange}
                       className="border-2 rounded-3xl px-[10px] py-[8px] border-black"
                />
                {/*<input type="text" placeholder="Bank Account No." className="border-2 rounded-3xl px-[10px] py-[8px] border-black"/>*/}
                <input
                    type="password"
                    placeholder="Password"
                    defaultValue={credentials.password}
                    value={formData.password}
                    onChange={handleChange}
                    className="border-2 rounded-3xl px-[10px] py-[8px] border-black"
                />
                <input
                    type="password"
                    placeholder="Confirm Password"
                    defaultValue={credentials.password}
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    className="border-2 rounded-3xl px-[10px] py-[8px] border-black"
                />
                {/*<div>*/}
                {/*    <input type="checkbox" />*/}
                {/*    <label> I am an owner</label>*/}
                {/*</div>*/}
                <button className="mt-[30px] rounded-3xl bg-black text-white px-[50px] py-[8px] font-semibold border-2 border-white">Register</button>
            </form>
        </div>
    );
}

export default RegisterPage;