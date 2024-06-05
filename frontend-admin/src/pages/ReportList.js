import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { useAuth0 } from '@auth0/auth0-react';
import { Button, Spinner } from "@nextui-org/react";
import { Link } from 'react-router-dom';

const ReportList = () => {
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [reports, setReports] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [reportType, setReportType] = useState('');
    const [reportStatus, setReportStatus] = useState('');

    const fetchReports = useCallback(async () => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.post('http://localhost:8080/api/report/search', {
                reportType: reportType || null,
                reportStatus: reportStatus || null
            }, {
                headers: { Authorization: `Bearer ${token}` },
                params: { page: 0, size: 10 }
            });
            setReports(response.data.content); // Assuming the reports are paginated and in the `content` field
            setLoading(false);
        } catch (error) {
            setError(error.message);
            setLoading(false);
        }
    }, [reportType, reportStatus]);

    useEffect(() => {
        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchReports();
        }
    }, [isAuthenticated, getIdTokenClaims, loginWithRedirect, fetchReports]);

    const handleEditReport = async (reportId, newStatus) => {
        try {
            const token = localStorage.getItem('authToken');
            const response = await axios.put('http://localhost:8080/api/report/', {
                reportId: reportId,
                reportStatus: newStatus
            }, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setReports(reports.map(report => report.reportId === reportId ? response.data : report));
        } catch (error) {
            setError(error.message);
        }
    };

    const handleDeleteReport = async (reportId) => {
        try {
            const token = localStorage.getItem('authToken');
            await axios.delete(`http://localhost:8080/api/report/${reportId}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setReports(reports.filter(report => report.reportId !== reportId));
        } catch (error) {
            setError(error.message);
        }
    };

    if (loading) {
        return <div className='flex items-center justify-center h-[100vh]'><Spinner label="Loading..." color="primary" labelColor="primary" /></div>;
    }

    if (error) {
        return <div className='flex flex-col w-full h-[100vh] items-center'>
        <h1 className='text-9xl font-bold'>404</h1>
        <p className='text-gray-700'>Unfortunately page you are searching for was not found.This might happen due to your internet connection or this page does not exist.</p>
    </div>;
    }

    return (
        <div className="p-4">
            <div className="mb-4">
                <label className="block text-gray-700">Report Type</label>
                <select
                    className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-opacity-50"
                    value={reportType}
                    onChange={e => setReportType(e.target.value)}
                >
                    <option value="">All</option>
                    <option value="USER">User</option>
                    <option value="SPACE">Space</option>
                </select>
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Report Status</label>
                <select
                    className="block w-full mt-1 p-2 border border-gray-300 rounded-md shadow-sm focus:ring focus:ring-opacity-50"
                    value={reportStatus}
                    onChange={e => setReportStatus(e.target.value)}
                >
                    <option value="">All</option>
                    <option value="PENDING">Pending</option>
                    <option value="SOLVED">Solved</option>
                    <option value="REJECTED">Rejected</option>
                </select>
            </div>
            <Button className="bg-blue-500 text-white px-4 py-2 rounded-md shadow-md" onClick={fetchReports}>Apply Filters</Button>
            <div className="mt-6 overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                    <tr>
                        <th className="py-2 px-4 border-b border-gray-200">Report ID</th>
                        <th className="py-2 px-4 border-b border-gray-200">Type</th>
                        <th className="py-2 px-4 border-b border-gray-200">Status</th>
                        <th className="py-2 px-4 border-b border-gray-200">Content</th>
                        <th className="py-2 px-4 border-b border-gray-200">Reporter</th>
                        <th className="py-2 px-4 border-b border-gray-200">Reported Entity</th>
                        <th className="py-2 px-4 border-b border-gray-200">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {reports.map(report => (
                        <tr key={report.reportId}>
                            <td className="py-2 px-4 border-b border-gray-200">{report.reportId}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{report.reportType}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{report.reportStatus}</td>
                            <td className="py-2 px-4 border-b border-gray-200">{report.reportContent}</td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                <Link to={`/user/${report.reporter.userId}`}>{report.reporter.userId}</Link>
                            </td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                {report.reportedUser ? (
                                    <Link to={`/user/${report.reportedUser.userId}`}>{report.reportedUser.userId}</Link>
                                ) : (
                                    <Link to={`/user/${report.reportedSpace.owner.userId}`}>{report.reportedSpace.owner.userId}</Link>
                                )}
                            </td>
                            <td className="py-2 px-4 border-b border-gray-200">
                                <Button className="bg-green-500 text-white px-4 py-2 rounded-md shadow-md mb-2" onClick={() => handleEditReport(report.reportId, 'SOLVED')}>Mark as Solved</Button>
                                <Button className="bg-red-500 text-white px-4 py-2 rounded-md shadow-md ml-2" onClick={() => handleDeleteReport(report.reportId)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ReportList;
