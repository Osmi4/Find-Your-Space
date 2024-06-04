import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import { Input, Button } from '@nextui-org/react';

const UserListPage = () => {
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({
        email: '',
        contactInfo: '',
        firstName: '',
        lastName: ''
    });
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);
    const navigate = useNavigate();

    const fetchUsers = useCallback(async () => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.post('http://localhost:8080/api/user/search', filters, {
                headers: { Authorization: `Bearer ${token}` },
                params: { page, size }
            });
            setUsers(response.data.content);
            setTotalPages(response.data.totalPages);
            setLoading(false);
        } catch (error) {
            setError(error.message);
            setLoading(false);
        }
    }, [filters, page, size]);

    useEffect(() => {
        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchUsers();
        }
    }, [isAuthenticated, getIdTokenClaims, loginWithRedirect, fetchUsers]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFilters({ ...filters, [name]: value });
    };

    const handlePageChange = (newPage) => {
        setPage(newPage);
    };

    const handleViewDetails = (userId) => {
        navigate(`/user/${userId}`);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div className="p-4">
            <h1 className="text-2xl font-semibold mb-4">User List</h1>
            <div className="mb-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                <Input
                    name="email"
                    label="Email"
                    placeholder="Filter by email"
                    value={filters.email}
                    onChange={handleChange}
                    fullWidth
                />
                <Input
                    name="contactInfo"
                    label="Contact Info"
                    placeholder="Filter by contact info"
                    value={filters.contactInfo}
                    onChange={handleChange}
                    fullWidth
                />
                <Input
                    name="firstName"
                    label="First Name"
                    placeholder="Filter by first name"
                    value={filters.firstName}
                    onChange={handleChange}
                    fullWidth
                />
                <Input
                    name="lastName"
                    label="Last Name"
                    placeholder="Filter by last name"
                    value={filters.lastName}
                    onChange={handleChange}
                    fullWidth
                />
            </div>
            <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md mb-4" onClick={fetchUsers}>Apply Filters</Button>
            <div className="mt-6 overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                    <tr>
                        <th className="py-2 px-4 border-b border-gray-200">User ID</th>
                        <th className="py-2 px-4 border-b border-gray-200">Email</th>
                        <th className="py-2 px-4 border-b border-gray-200">Contact Info</th>
                        <th className="py-2 px-4 border-b border-gray-200">First Name</th>
                        <th className="py-2 px-4 border-b border-gray-200">Last Name</th>
                        <th className="py-2 px-4 border-b border-gray-200">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map(user => (
                        <tr key={user.userId}>
                            <td className="py-2 px-4 border-b border-gray-200">{user.userId}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{user.email}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{user.contactInfo}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{user.firstName}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{user.lastName}</td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md" onClick={() => handleViewDetails(user.userId)}>View Details</Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
            <div className="mt-4 flex justify-between">
                <Button
                    className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md"
                    onClick={() => handlePageChange(page - 1)}
                    disabled={page === 0}
                >
                    Previous
                </Button>
                <span>Page {page + 1} of {totalPages}</span>
                <Button
                    className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md"
                    onClick={() => handlePageChange(page + 1)}
                    disabled={page === totalPages - 1}
                >
                    Next
                </Button>
            </div>
        </div>
    );
};

export default UserListPage;
