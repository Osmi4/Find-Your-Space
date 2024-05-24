import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, useDisclosure, Input } from "@nextui-org/react";
import { LockIcon, MailIcon } from "lucide-react";
import { useState } from "react";
import axios from "axios";

const RegisterModal = () => {
    const { isOpen, onOpen, onOpenChange } = useDisclosure();
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
        contactInfo: '',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async () => {
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
                contactInfo: formData.contactInfo,
            });

            if (response.status === 200) {
                const token = response.data.token;
                localStorage.setItem('token', token);
                alert("Registration successful!");
                onOpenChange(false); // close the modal after successful registration
            }
        } catch (error) {
            console.error("There was an error registering!", error);
            alert("Registration failed!");
        }
    };

    return (
        <>
            <button onClick={onOpen}>Register</button>
            <Modal
                isOpen={isOpen}
                onOpenChange={onOpenChange} // manage modal open state
                placement="top-center"
            >
                <ModalContent>
                    <ModalHeader className="flex flex-col gap-1">Register</ModalHeader>
                    <ModalBody>
                        <Input
                            autoFocus
                            label="First Name"
                            name="firstName"
                            placeholder="Enter your first name"
                            variant="bordered"
                            onChange={handleChange}
                        />
                        <Input
                            label="Last Name"
                            name="lastName"
                            placeholder="Enter your last name"
                            variant="bordered"
                            onChange={handleChange}
                        />
                        <Input
                            label="Contact Info"
                            name="contactInfo"
                            placeholder="Enter your contact info"
                            variant="bordered"
                            onChange={handleChange}
                        />
                        <Input
                            endContent={
                                <MailIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                            }
                            label="Email"
                            name="email"
                            placeholder="Enter your email"
                            variant="bordered"
                            onChange={handleChange}
                        />
                        <Input
                            endContent={
                                <LockIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                            }
                            label="Password"
                            name="password"
                            placeholder="Enter your password"
                            type="password"
                            variant="bordered"
                            onChange={handleChange}
                        />
                        <Input
                            endContent={
                                <LockIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                            }
                            label="Confirm Password"
                            name="confirmPassword"
                            placeholder="Enter your password"
                            type="password"
                            variant="bordered"
                            onChange={handleChange}
                        />
                    </ModalBody>
                    <ModalFooter>
                        <Button color="danger" variant="flat" onPress={() => onOpenChange(false)}>
                            Close
                        </Button>
                        <Button color="primary" onPress={handleSubmit}>
                            Sign up
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
};

export default RegisterModal;
