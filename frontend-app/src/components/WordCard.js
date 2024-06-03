import React, { useEffect } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import RegisterModal from './RegisterModal';

const WordCard = () => {
    const { loginWithRedirect, isAuthenticated, getIdTokenClaims } = useAuth0();

    useEffect(() => {
        const storeToken = async () => {
            if (isAuthenticated) {
                const tokenClaims = await getIdTokenClaims();
                const token = tokenClaims.__raw;
                localStorage.setItem('authToken', token);
            }
        };

        storeToken();
    }, [isAuthenticated, getIdTokenClaims]);

    return (
        <div className="wordcard">
            <h1>Welcome to Find Your Space!</h1>
            <div className="signup">
                <p>If you have an account please <a href="#" onClick={() => loginWithRedirect()}>Log in</a></p>
            </div>
        </div>
    );
};

export default WordCard;
