import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Input, Button } from '@nextui-org/react';

const MessagePersonPage = () => {
    const { id } = useParams();
    const { isAuthenticated } = useAuth0();
    const [formData, setFormData] = useState({
        messageContent: '',
        receiverId: id || '',
    });
    const [messages, setMessages] = useState([]);
    const [recipient, setRecipient] = useState(null);

    useEffect(() => {
        const fetchMessagesAndRecipient = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const [messagesResponse, recipientResponse] = await Promise.all([
                    axios.get(`http://localhost:8080/api/message/user/${id}`, {
                        headers: { Authorization: `Bearer ${token}` },
                        params: { page: 0, size: 10 }
                    }),
                    axios.get(`http://localhost:8080/api/user/${id}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                ]);

                setMessages(messagesResponse.data.content);
                setRecipient(recipientResponse.data);
                setFormData((prevFormData) => ({
                    ...prevFormData,
                    messageDestinationEmail: recipientResponse.data.email,
                }));
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        if (isAuthenticated) {
            fetchMessagesAndRecipient();
        }
    }, [id, isAuthenticated]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('authToken');
        try {
            const response = await axios.post('http://localhost:8080/api/message', formData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            console.log('Message sent:', response.data);
            setMessages((prevMessages) => [...prevMessages, response.data]);
            setFormData({ ...formData, messageContent: '' });
        } catch (error) {
            console.error("Error sending message:", error);
        }
    };

    if (!isAuthenticated) return <div>Please log in to send a message.</div>;

    return (
        <div className="container mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Send a New Message</h1>
            {recipient && (
                <div className="mb-4 p-4 bg-gray-100 rounded-lg">
                    <h2 className="text-xl font-semibold">Recipient Information</h2>
                    <p><strong>Name:</strong> {recipient.firstName} {recipient.lastName}</p>
                    <p><strong>Email:</strong> {recipient.email}</p>
                    <p><strong>Contact Info:</strong> {recipient.contactInfo}</p>
                </div>
            )}
            <form onSubmit={handleSubmit} className="grid grid-cols-1 gap-4">
                <Input
                    name="messageContent"
                    label="Message Content"
                    placeholder="Enter your message"
                    variant="bordered"
                    value={formData.messageContent}
                    onChange={handleChange}
                    fullWidth
                />
                <Button color="primary" type="submit" className="w-full text-lg py-2">Send Message</Button>
            </form>
            <div className="mt-8">
                <h2 className="text-xl font-bold mb-4">Previous Messages</h2>
                {messages.length === 0 ? (
                    <p>No previous messages</p>
                ) : (
                    <div className="grid grid-cols-1 gap-4">
                        {messages.map(message => (
                            <div key={message.messageId} className="bg-white p-4 rounded-lg shadow-md">
                                <p className="text-lg font-semibold">{message.messageContent}</p>
                                <p className="text-gray-600">To: {recipient.firstName} {recipient.lastName}</p>
                                <p className="text-gray-600">Received: {new Date(message.messageDateTime).toLocaleString()}</p>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default MessagePersonPage;
