import { useEffect, useState } from "react";
import axios from "axios";
import { Input, Button } from "@nextui-org/react";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { ClipLoader } from "react-spinners";

const AccountPage = () => {
    const [userInfo, setUserInfo] = useState({});
    const [error, setError] = useState(null);
    const [validationError, setValidationError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [loading, setLoading] = useState(true);
    const [updatedDetails, setUpdatedDetails] = useState({
        firstName: "",
        lastName: "",
        contactInfo: "",
        bankAccountNumber: ""
    });

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get('http://localhost:8080/api/user/my-details', {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                setUserInfo(response.data);
                setUpdatedDetails({
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    contactInfo: response.data.contactInfo,
                    bankAccountNumber: ""
                });
                await fetchBankAccount(response.data.id);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching user info", error);
                setError(error);
                setLoading(false);
            }
        };

        const fetchBankAccount = async (userId) => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.get(`http://localhost:8080/api/user/getMyBankAccount`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                setUpdatedDetails(prevDetails => ({
                    ...prevDetails,
                    bankAccountNumber: response.data
                }));
            } catch (error) {
                console.error("Error fetching bank account info", error);
                setError(error);
            }
        };

        fetchUserInfo();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUpdatedDetails(prevDetails => ({
            ...prevDetails,
            [name]: value
        }));
    };

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleCancel = () => {
        setIsEditing(false);
        setUpdatedDetails({
            firstName: userInfo.firstName,
            lastName: userInfo.lastName,
            contactInfo: userInfo.contactInfo,
            bankAccountNumber: updatedDetails.bankAccountNumber
        });
    };

    const handleSubmit = async () => {
        // Validation
        if (!/^\d{9}$/.test(updatedDetails.contactInfo)) {
            setValidationError("Contact Info must be a 9-digit number.");
            return;
        }
        if (!/^\d{16}$/.test(updatedDetails.bankAccountNumber)) {
            setValidationError("Bank Account Number must be a 16-digit number.");
            return;
        }

        setValidationError(null);

        const token = localStorage.getItem('authToken');
        try {
            await axios.patch(`http://localhost:8080/api/user/my-details`, updatedDetails, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            setUserInfo(prevInfo => ({
                ...prevInfo,
                firstName: updatedDetails.firstName,
                lastName: updatedDetails.lastName,
                contactInfo: updatedDetails.contactInfo,
                bankAccountNumber: updatedDetails.bankAccountNumber
            }));
            setIsEditing(false);
            toast.success("Details updated successfully!");
        } catch (error) {
            console.error("Error updating user details", error);
            setError(error);
            toast.error("Error updating user details.");
        }
    };

    return (
        <div className="container mx-auto p-4">
            <ToastContainer />
            <div className="bg-white shadow-md rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-4">Account Details</h1>
                {loading ? (
                    <div className="flex justify-center items-center">
                        <ClipLoader size={50} color={"#123abc"} loading={loading} />
                    </div>
                ) : error ? (
                    <p className="text-red-500">Error fetching user info: {error.message}</p>
                ) : (
                    <>
                        {isEditing ? (
                            <>
                                <div className="mb-4">
                                    <Input
                                        label="First Name"
                                        type="text"
                                        name="firstName"
                                        value={updatedDetails.firstName}
                                        onChange={handleInputChange}
                                        className="w-full text-gray-700"
                                        variant="bordered"
                                    />
                                </div>
                                <div className="mb-4">
                                    <Input
                                        label="Last Name"
                                        type="text"
                                        name="lastName"
                                        value={updatedDetails.lastName}
                                        onChange={handleInputChange}
                                        className="w-full text-gray-700"
                                        variant="bordered"
                                    />
                                </div>
                                <div className="mb-4">
                                    <Input
                                        label="Contact Info"
                                        type="text"
                                        name="contactInfo"
                                        value={updatedDetails.contactInfo}
                                        onChange={handleInputChange}
                                        className="w-full text-gray-700"
                                        variant="bordered"
                                    />
                                </div>
                                <div className="mb-4">
                                    <Input
                                        label="Bank Account Number"
                                        type="text"
                                        name="bankAccountNumber"
                                        value={updatedDetails.bankAccountNumber}
                                        onChange={handleInputChange}
                                        className="w-full text-gray-700"
                                        variant="bordered"
                                    />
                                </div>
                                {validationError && (
                                    <p className="text-red-500">{validationError}</p>
                                )}
                                <div className="flex items-center justify-between">
                                    <Button
                                        onClick={handleSubmit}
                                        className="font-bold"
                                        color="primary"
                                    >
                                        Save
                                    </Button>
                                    <Button
                                        onClick={handleCancel}
                                        className="font-bold"
                                        color="danger"
                                    >
                                        Cancel
                                    </Button>
                                </div>
                            </>
                        ) : (
                            <>
                                <Input
                                    isReadOnly
                                    label="First Name"
                                    type="text"
                                    name="firstName"
                                    value={userInfo.firstName}
                                    className="w-full text-gray-700 mb-2"
                                    variant="bordered"
                                />
                                <Input
                                    isReadOnly
                                    label="Last Name"
                                    type="text"
                                    name="lastName"
                                    value={userInfo.lastName}
                                    className="w-full text-gray-700 mb-2"
                                    variant="bordered"
                                />
                                <Input
                                    isReadOnly
                                    label="Email"
                                    type="text"
                                    name="email"
                                    value={userInfo.email}
                                    className="w-full text-gray-700 mb-2"
                                    variant="bordered"
                                />
                                <Input
                                    isReadOnly
                                    label="Contact Info"
                                    type="text"
                                    name="contactInfo"
                                    value={userInfo.contactInfo}
                                    className="w-full text-gray-700 mb-2"
                                    variant="bordered"
                                />
                                <Input
                                    isReadOnly
                                    label="Bank Account Number"
                                    type="text"
                                    name="bankAccountNumber"
                                    value={updatedDetails.bankAccountNumber}
                                    className="w-full text-gray-700 mb-2"
                                    variant="bordered"
                                />
                                <Button
                                    onClick={handleEdit}
                                    className="font-bold bg-black text-white"
                                    color="primary"
                                    variant="bordered"
                                >
                                    Edit
                                </Button>
                            </>
                        )}
                    </>
                )}
            </div>
        </div>
    );
};

export default AccountPage;
