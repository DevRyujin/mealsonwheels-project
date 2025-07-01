import React from "react";
import { useNavigate } from "react-router-dom";

const RegistrationSuccessPage = () => {
  const navigate = useNavigate();

  return (
    <div className="min-h-screen bg-gray-100 py-20 px-4">
      <div className="text-center space-y-6">
        <h1 className="text-4xl font-bold text-green-600">Registration Submitted!</h1>
        <p className="text-lg text-gray-700">
          Your account request has been submitted and is awaiting admin approval.
        </p>

        <div className="flex flex-col sm:flex-row gap-4 justify-center mt-8">
          <button
            onClick={() => navigate("/")}
            className="px-6 py-3 rounded-xl border border-gray-400 text-gray-700 hover:bg-gray-200 transform transition-transform active:scale-95"
          >
            Back to Home
          </button>
          <button
            onClick={() => navigate("/login")}
            className="px-6 py-3 rounded-xl bg-gradient-to-r from-orange-500 to-red-500 text-white hover:brightness-110 transform transition-transform active:scale-95"
          >
            Go to Login
          </button>
        </div>
      </div>
    </div>
  );
};

export default RegistrationSuccessPage;
