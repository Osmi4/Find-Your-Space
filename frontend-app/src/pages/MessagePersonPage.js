import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { useAuth0 } from '@auth0/auth0-react';
import { Input, Button } from '@nextui-org/react';
import { ClipLoader } from 'react-spinners';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const MessagePersonPage = () => {
    const { id } = useParams();
    const { isAuthenticated } = useAuth0();
    const [formData, setFormData] = useState({
        messageContent: '',
        receiverId: id || '',
    });
    const [messages, setMessages] = useState([]);
    const [recipient, setRecipient] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);

    useEffect(() => {
        const fetchMessagesAndUsers = async () => {
            const token = localStorage.getItem('authToken');
            try {
                const [messagesResponse, recipientResponse, currentUserResponse] = await Promise.all([
                    axios.get(`http://localhost:8080/api/message/user/${id}`, {
                        headers: { Authorization: `Bearer ${token}` },
                        params: { page: page, size: 10 }
                    }),
                    axios.get(`http://localhost:8080/api/user/${id}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    }),
                    axios.get(`http://localhost:8080/api/user/my-details`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                ]);

                if (page === 0) {
                    setMessages(messagesResponse.data.content);
                } else {
                    setMessages((prevMessages) => [...messagesResponse.data.content, ...prevMessages]);
                }

                setRecipient(recipientResponse.data);
                setCurrentUser(currentUserResponse.data);
                setFormData((prevFormData) => ({
                    ...prevFormData,
                    messageDestinationEmail: recipientResponse.data.email,
                }));
                setHasMore(messagesResponse.data.content.length > 0);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching data:", error);
                setLoading(false);
            }
        };

        if (isAuthenticated) {
            fetchMessagesAndUsers();
        }
    }, [id, isAuthenticated, page]);

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
            toast.success('Message sent successfully');
            setMessages((prevMessages) => [response.data, ...prevMessages]);
            setFormData({ ...formData, messageContent: '' });
        } catch (error) {
            console.error("Error sending message:", error);
            toast.error('Error sending message');
        }
    };

    const loadMoreMessages = () => {
        setPage((prevPage) => prevPage + 1);
    };

    const loadPreviousMessages = () => {
        if (page > 0) {
            setPage((prevPage) => prevPage - 1);
        }
    };

    if (!isAuthenticated) return <div>Please log in to send a message.</div>;

    if (loading && page === 0) {
        return (
            <div className="flex justify-center items-center h-screen">
                <ClipLoader size={35} color={"#123abc"} loading={loading} />
            </div>
        );
    }

    return (
        <div className="container mx-auto p-4">
            <ToastContainer />
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
                    <div className="flex flex-col gap-4">
                        {messages.map(message => (
                            <div
                                key={message.messageId}
                                className={`p-4 rounded-lg shadow-md ${message.senderId === currentUser.userId ? 'bg-blue-100 self-end' : 'bg-green-100 self-start'}`}
                                style={{ maxWidth: '75%', alignSelf: message.senderId === currentUser.userId ? 'flex-end' : 'flex-start' }}
                            >
                                <p className="text-lg font-semibold">{message.messageContent}</p>
                                <p className="text-gray-600">{message.senderId === currentUser.userId ? 'You' : `${recipient.firstName} ${recipient.lastName}`}</p>
                                <p className="text-gray-600">Received: {new Date(message.messageDateTime).toLocaleString()}</p>
                            </div>
                        ))}
                    </div>
                )}
                <div className="flex justify-between mt-4">
                    <Button color="primary" disabled={page === 0} onClick={loadPreviousMessages}>
                        Previous
                    </Button>
                    <Button color="primary" disabled={!hasMore} onClick={loadMoreMessages}>
                        Next
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default MessagePersonPage;
