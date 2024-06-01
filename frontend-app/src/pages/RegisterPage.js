import React from 'react';
import { useAuth0 } from '@auth0/auth0-react';

const RegisterPage = () => {
    const { loginWithRedirect } = useAuth0();

    const handleRegister = () => {
        loginWithRedirect({
            screen_hint: 'signup',
        });
    };

    return (
        <div className="mt-[150px] flex justify-center">
            <div className="flex flex-col gap-[10px] items-center border-2 py-[20px] border-black rounded-3xl bg-slate-100 w-[300px]">
                <h1 className="text-center mb-[30px] text-3xl font-semibold underline">New User</h1>
                <button
                    onClick={handleRegister}
                    className="mt-[30px] rounded-3xl bg-black text-white px-[50px] py-[8px] font-semibold border-2 border-white"
                >
                    Register with Auth0
                </button>
            </div>
        </div>
    );
};

export default RegisterPage;
