import  { useState, useEffect } from 'react';
import './App.css';
import {useAuth0} from '@auth0/auth0-react';

function SpacesContainer({ spaces }) {
    return (
        <div className="section">
            <h2>Spaces</h2>
            <ul className="spaces-list">
                {spaces.map(space => (
                    <li key={space.spaceId} className="space-item">
                        <h3>{space.name}</h3>
                        <img src={space.imageUrl} alt={space.name} />
                    </li>
                ))}
            </ul>
        </div>
    );
}

function UserContainer({users}) {
    return (
      <div className="section">
      <h2>Users</h2>
      {<ul className="spaces-list">
          {users.map(user => (
              <li key={user.user_id} className="space-item">
                  <h3>{user.first_name} {user.last_name}</h3>
                  <img src={user.pictureUrl} alt={user.first_name} />
              </li>
          ))}
      </ul> }
  </div>
  );
}
function BookingsContainer({ bookings, handleDeleteBooking }) {
    return (
        <div className="section">
            <h2>Bookings</h2>
            <ul className="bookings-list">
                {bookings.map(booking => (
                    <li key={booking.id} className="booking-item">
                        {booking.details}
                        <button onClick={() => handleDeleteBooking(booking.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

function UserReportsContainer({ userReports }) {
    const handleReviewReport = (id) => {
        // Handle reviewing the report
        console.log("Reviewing report with ID:", id);
    };

    return (
        <div className="section">
            <h2>User Reports</h2>
            <ul className="user-reports-list">
                {userReports.map(report => (
                    <li key={report.id} className="user-report-item">
                        <p>User: {report.user}</p>
                        <p>Report: {report.details}</p>
                        <button onClick={() => handleReviewReport(report.id)}>Review</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}


function App() {

    const { user, isAuthenticated, isLoading, getIdTokenClaims, loginWithRedirect } = useAuth0();
    const [spaces, setSpaces] = useState([]);
    const [users, setUsers] = useState([]);
    const [bookings, setBookings] = useState([]);
    const [userReports, setUserReports] = useState([]);

    useEffect(() => {
        const fetchToken = async () => {
            if (isAuthenticated) {
                const tokenClaims = await getIdTokenClaims();
                const token = tokenClaims.__raw;
                localStorage.setItem('authToken', token);
            }
        }

        const fetchImage = async (spaceId, token) => {
                try {
                    const response = await fetch(`http://localhost:8080/api/space/${spaceId}/images`, {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });
                    return response.data;
                } catch (error) {
                    console.error('Error fetching image:', error);
                    return null;
                }
            };

        const fetchSpaces = async() => {
            const token = localStorage.getItem('authToken');
            if(token){
                try {
                    const response = await fetch('http://localhost:8080/api/space/all', {
                    method: 'GET', headers: { Authorization: `Bearer ${token}`}});
                    console.log(response);
                    const spacesWithImages =
                    await Promise.all(response.data.content.map(async space => {
                    const imageUrl = await fetchImage(space.spaceId, token);
                                return { ...space, imageUrl };}));

                 setSpaces(spacesWithImages);
                } catch (error) {
                    console.error("Error loading users", error);
                }
            }

            // const simulatedSpaces = [
            //     { spaceId: 1, name: 'Space 1', imageUrl: 'https://via.placeholder.com/150' },
            //     { spaceId: 2, name: 'Space 2', imageUrl: 'https://via.placeholder.com/150' },
            //     { spaceId: 3, name: 'Space 3', imageUrl: 'https://via.placeholder.com/150' },
            // ];
            // setSpaces(simulatedSpaces);
        };

        const fetchBookings = () => {
            const simulatedBookings = [
                { id: 1, details: 'Booking 1' },
                { id: 2, details: 'Booking 2' },
                { id: 3, details: 'Booking 3' },
            ];
            setBookings(simulatedBookings);
        };

        const fetchUserReports = () => {
            const simulatedUserReports = [
                { id: 1, user: 'User 1', details: 'Report 1' },
                { id: 2, user: 'User 2', details: 'Report 2' },
                { id: 3, user: 'User 3', details: 'Report 3' },
            ];
            setUserReports(simulatedUserReports);
        };

        const fetchUsers = async() => {
            const token = localStorage.getItem('authToken');
            if(token){
                try {

                    const response = await fetch('http://localhost:8080/user/95326876-cafb-4c82-8584-49c82569b13c', {method: 'GET',
                        headers: { Authorization: `Bearer ${token}`}});
                    console.log(response);
                    setUsers([response]);
                } catch (error) {
                    console.error("Error loading users", error);
                }
            }


        }

            fetchToken();
            fetchUsers();
            fetchSpaces();
            fetchBookings();
            fetchUserReports();
    }, [getIdTokenClaims]);

    const handleDeleteBooking = (id) => {
        const isConfirmed = window.confirm("Are you sure you want to delete this booking?");
        if (isConfirmed) {
            setBookings(bookings.filter(booking => booking.id !== id));
        }
    };

    return (
        <div className="App">
            <header className="App-main-header">
                <h1>FindYourSpace Admin Panel</h1>
                {!isAuthenticated && <button onClick={() => loginWithRedirect()}>
                    Login
                </button>}
            </header>
            <div className="App-header">
                <div className="App-content">
                    <SpacesContainer spaces={spaces} />
                    <BookingsContainer bookings={bookings} handleDeleteBooking={handleDeleteBooking} />
                    <UserReportsContainer userReports={userReports} />
                    <UserContainer users = {users}/>
                </div>
            </div>
        </div>
    );
}

export default App;
