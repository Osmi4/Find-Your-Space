import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { useNavigate } from 'react-router-dom';
import { Button, Pagination } from '@nextui-org/react';

const MyReportsPage = () => {
    const { isAuthenticated } = useAuth0();
    const navigate = useNavigate();
    const [reports, setReports] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);

    useEffect(() => {
        const fetchReports = async (page) => {
            const token = localStorage.getItem('authToken');
            try {
                const response = await axios.get('http://localhost:8080/api/report/my-reports', {
                    headers: { Authorization: `Bearer ${token}` },
                    params: { page: page, size: 10 }
                });
                setReports(response.data.content);
                setTotalPages(response.data.totalPages);
            } catch (error) {
                console.error("Error fetching reports:", error);
            }
        };

        if (isAuthenticated) {
            fetchReports(currentPage);
        }
    }, [isAuthenticated, currentPage]);

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    if (!isAuthenticated) return <div>Please log in to view your reports.</div>;

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">My Reports</h1>
            {reports.length === 0 ? (
                <p className='text-lg'>No reports found.</p>
            ) : (
                <>
                <div className="grid grid-cols-1 gap-4">
                    {reports.map(report => (
                        <div key={report.reportId} className="bg-white p-4 rounded-lg shadow-md">
                            <p className="text-lg font-semibold">Report Type: {report.reportType}</p>
                            <p className="text-gray-600">Status: {report.reportStatus}</p>
                            <p className="text-gray-600">Content: {report.reportContent}</p>
                            <p className="text-gray-600">Reported On: {new Date(report.reportDateTime).toLocaleString()}</p>
                            {report.reportedUser && (
                                <div>
                                    <p className="text-gray-600">Reported User: {report.reportedUser.firstName} {report.reportedUser.lastName}</p>
                                    <p className="text-gray-600">Email: {report.reportedUser.email}</p>
                                </div>
                            )}
                            {report.reportedSpace && (
                                <div>
                                    <p className="text-gray-600">Reported Space: {report.reportedSpace.spaceName}</p>
                                    <p className="text-gray-600">Description: {report.reportedSpace.spaceDescription}</p>
                                </div>
                            )}
                        </div>
                    ))}
                </div>
                <div className="flex justify-center mt-4">
                <Pagination
                    total={totalPages}
                    initialPage={currentPage + 1}
                    onChange={(page) => handlePageChange(page - 1)}
                />
            </div>
            </>
            )}
            
        </div>
    );
};

export default MyReportsPage;
