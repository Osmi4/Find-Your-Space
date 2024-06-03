import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import axios from 'axios';
import { Input, Button, RadioGroup, Radio } from '@nextui-org/react';

const ReportPage = () => {
    const { id } = useParams(); // ID is always space ID
    const navigate = useNavigate();
    const { isAuthenticated } = useAuth0();
    const [formData, setFormData] = useState({
        reportType: 'SPACE',
        reportContent: '',
        reportedId: id || '',
    });
    const [reportedEntity, setReportedEntity] = useState(null);
    const [ownerInfo, setOwnerInfo] = useState(null);

    useEffect(() => {
        const fetchEntityDetails = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const response = await axios.get(`http://localhost:8080/api/space/${id}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                const spaceData = response.data;
                setReportedEntity(spaceData);
                if (formData.reportType === 'USER' && spaceData.owner) {
                    const ownerResponse = await axios.get(`http://localhost:8080/api/user/${spaceData.owner.userId}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    });
                    setOwnerInfo(ownerResponse.data);
                    setFormData((prevFormData) => ({
                        ...prevFormData,
                        reportedId: spaceData.owner.userId
                    }));
                }
            } catch (error) {
                console.error("Error fetching entity details:", error);
            }
        };

        if (isAuthenticated) {
            fetchEntityDetails();
        }
    }, [id, isAuthenticated]);

    useEffect(() => {
        const updateReportedId = async () => {
            const token = localStorage.getItem('authToken');
            if (formData.reportType === 'USER' && reportedEntity && reportedEntity.owner) {
                const ownerResponse = await axios.get(`http://localhost:8080/api/user/${reportedEntity.owner.userId}`, {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setOwnerInfo(ownerResponse.data);
                setFormData((prevFormData) => ({
                    ...prevFormData,
                    reportedId: reportedEntity.owner.userId
                }));
            } else {
                setFormData((prevFormData) => ({
                    ...prevFormData,
                    reportedId: id
                }));
            }
        };

        if (reportedEntity) {
            updateReportedId();
        }
    }, [formData.reportType, reportedEntity]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleRadioChange = (value) => {
        setFormData({ ...formData, reportType: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('authToken');
        try {
            const response = await axios.post('http://localhost:8080/api/report/', formData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            console.log('Report submitted:', response.data);
            navigate('/');
        } catch (error) {
            console.error("Error submitting report:", error);
        }
    };

    if (!isAuthenticated) return <div>Please log in to submit a report.</div>;

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Report Page</h1>
            <div className="mb-4 p-4 bg-gray-100 rounded-lg">
                <h2 className="text-xl font-semibold">Reported Entity Information</h2>
                {reportedEntity && formData.reportType === 'SPACE' && (
                    <div>
                        <p><strong>Space Name:</strong> {reportedEntity.spaceName}</p>
                        <p><strong>Description:</strong> {reportedEntity.spaceDescription}</p>
                    </div>
                )}
                {ownerInfo && formData.reportType === 'USER' && (
                    <div>
                        <p><strong>Name:</strong> {ownerInfo.firstName} {ownerInfo.lastName}</p>
                        <p><strong>Email:</strong> {ownerInfo.email}</p>
                        <p><strong>Contact Info:</strong> {ownerInfo.contactInfo}</p>
                    </div>
                )}
            </div>
            <form onSubmit={handleSubmit} className="grid grid-cols-1 gap-4">
                <RadioGroup value={formData.reportType} onChange={(e) => handleRadioChange(e.target.value)}>
                    <Radio value="SPACE">Report Space</Radio>
                    <Radio value="USER">Report User</Radio>
                </RadioGroup>
                <Input
                    name="reportContent"
                    placeholder="Enter your report content"
                    value={formData.reportContent}
                    onChange={handleChange}
                    fullWidth
                />
                <Button color="primary" type="submit" className="w-full text-lg py-2">Submit Report</Button>
            </form>
        </div>
    );
};

export default ReportPage;
