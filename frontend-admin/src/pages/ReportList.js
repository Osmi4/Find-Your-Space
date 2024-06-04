import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button, Table } from '@nextui-org/react';
import { useAuth0 } from '@auth0/auth0-react';

const ReportList = () => {
    const { getIdTokenClaims, isAuthenticated, loginWithRedirect } = useAuth0();
    const [reports, setReports] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchReports = async () => {
            try {
                const token = localStorage.getItem('authToken');
                const response = await axios.post('http://localhost:8080/api/report/search', {
                    headers: { Authorization: `Bearer ${token}` },
                    params: { page: 0, size: 10 }
                });
                setReports(response.data.content); // Assuming the reports are paginated and in the `content` field
                setLoading(false);
            } catch (error) {
                setError(error.message);
                setLoading(false);
            }
        };

        if (!isAuthenticated) {
            loginWithRedirect();
        } else {
            fetchReports();
        }
    }, [isAuthenticated, getIdTokenClaims, loginWithRedirect]);

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

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    return (
        <div>
            <Table>
                <Table.Header>
                    <Table.Column>Report ID</Table.Column>
                    <Table.Column>Type</Table.Column>
                    <Table.Column>Status</Table.Column>
                    <Table.Column>Content</Table.Column>
                    <Table.Column>Action</Table.Column>
                </Table.Header>
                <Table.Body>
                    {reports.map(report => (
                        <Table.Row key={report.reportId}>
                            <Table.Cell>{report.reportId}</Table.Cell>
                            <Table.Cell>{report.reportType}</Table.Cell>
                            <Table.Cell>{report.reportStatus}</Table.Cell>
                            <Table.Cell>{report.reportContent}</Table.Cell>
                            <Table.Cell>
                                <Button onClick={() => handleEditReport(report.reportId, 'SOLVED')}>Mark as Solved</Button>
                            </Table.Cell>
                        </Table.Row>
                    ))}
                </Table.Body>
            </Table>
        </div>
    );
};

export default ReportList;
