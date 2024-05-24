import { useEffect, useState } from "react";
import axios from "axios";

const AccountPage = () => {
    const [userInfo, setUserInfo] = useState({});
    const token = localStorage.getItem('token');

    useEffect(() => {
        if (token) {
            axios.get('http://localhost:8080/api/user/my-details', {
                headers: { 'Authorization': `Bearer ${token}` }
            }).then(response => {
                setUserInfo(response.data);
            }).catch(error => {
                console.error("Error fetching user info", error);
            });
        }
    }, [token]);

    return (
        <div className="container mx-auto p-4">
            <div className="bg-white shadow-md rounded-lg p-6">
                <h1 className="text-2xl font-bold mb-4">Account Details</h1>
                <p className="mb-2"><span className="font-semibold">Name:</span> {userInfo.firstName} {userInfo.lastName}</p>
                <p className="mb-2"><span className="font-semibold">Email:</span> {userInfo.email}</p>
                <p className="mb-2"><span className="font-semibold">Contact Info:</span> {userInfo.contactInfo}</p>
            </div>
        </div>
    );
};

export default AccountPage;
