import React from 'react';
import { useNavigate } from 'react-router-dom';

const RegistrationSuccessPage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 px-4">
            <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md text-center">
                <h1 className="text-2xl font-semibold text-green-600 mb-4">Registration Submitted!</h1>
                <p className="mb-6 text-gray-700">
                    Your account request has been submitted and is awaiting admin approval.
                </p>
                <div className="flex gap-4 justify-center">
                    <button
                        onClick={() => navigate('/')}
                        className="bg-gray-500 hover:bg-gray-600 text-white px-4 py-2 rounded-xl"
                    >
                        Back to Home
                    </button>
                    <button
                        onClick={() => navigate('/login')}
                        className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-xl"
                    >
                        Go to Login
                    </button>
                </div>
            </div>
        </div>
    );
};

export default RegistrationSuccessPage;
