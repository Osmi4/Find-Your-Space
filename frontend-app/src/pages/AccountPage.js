import { useEffect, useState } from "react";
import axios from "axios";

const AccountPage = () => {
    const [userInfo, setUserInfo] = useState({});
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
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
                fetchBankAccount(response.data.id);
            } catch (error) {
                console.error("Error fetching user info", error);
                setError(error);
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
        } catch (error) {
            console.error("Error updating user details", error);
            setError(error);
        }
    };

    return (
        <div className="container mx-auto p-4">
            <div className="bg-white shadow-md rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-4">Account Details</h1>
                {error ? (
                    <p className="text-red-500">Error fetching user info: {error.message}</p>
                ) : (
                    <>
                        {isEditing ? (
                            <>
                                <div className="mb-4">
                                    <label className="block text-gray-700 text-sm font-bold mb-2">First Name</label>
                                    <input
                                        type="text"
                                        name="firstName"
                                        value={updatedDetails.firstName}
                                        onChange={handleInputChange}
                                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                </div>
                                <div className="mb-4">
                                    <label className="block text-gray-700 text-sm font-bold mb-2">Last Name</label>
                                    <input
                                        type="text"
                                        name="lastName"
                                        value={updatedDetails.lastName}
                                        onChange={handleInputChange}
                                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                </div>
                                <div className="mb-4">
                                    <label className="block text-gray-700 text-sm font-bold mb-2">Contact Info</label>
                                    <input
                                        type="text"
                                        name="contactInfo"
                                        value={updatedDetails.contactInfo}
                                        onChange={handleInputChange}
                                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                </div>
                                <div className="mb-4">
                                    <label className="block text-gray-700 text-sm font-bold mb-2">Bank Account Number</label>
                                    <input
                                        type="text"
                                        name="bankAccountNumber"
                                        value={updatedDetails.bankAccountNumber}
                                        onChange={handleInputChange}
                                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    />
                                </div>
                                <div className="flex items-center justify-between">
                                    <button
                                        onClick={handleSubmit}
                                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    >
                                        Save
                                    </button>
                                    <button
                                        onClick={handleCancel}
                                        className="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                    >
                                        Cancel
                                    </button>
                                </div>
                            </>
                        ) : (
                            <>
                                <p className="mb-2"><span className="font-semibold">First Name:</span> {userInfo.firstName}</p>
                                <p className="mb-2"><span className="font-semibold">Last Name:</span> {userInfo.lastName}</p>
                                <p className="mb-2"><span className="font-semibold">Email:</span> {userInfo.email}</p>
                                <p className="mb-2"><span className="font-semibold">Contact Info:</span> {userInfo.contactInfo}</p>
                                <p className="mb-2"><span className="font-semibold">Bank Account Number:</span> {updatedDetails.bankAccountNumber}</p>
                                <button
                                    onClick={handleEdit}
                                    className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
                                >
                                    Edit
                                </button>
                            </>
                        )}
                    </>
                )}
            </div>
        </div>
    );
};

export default AccountPage;
