import React from "react";

const ApprovalPendingPage = () => {
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="max-w-md w-full p-8 border border-gray-300 rounded-xl shadow-md bg-white text-center">
        <h2 className="text-2xl font-bold text-yellow-600 mb-4">
          Account Pending Approval
        </h2>
        <p className="text-gray-700">
          Your account is awaiting admin approval.
          <br />
          You will be notified via email once it’s approved.
        </p>
        <div className="mt-6">
          <a
            href="/login"
            className="text-blue-600 hover:underline font-medium"
          >
            Return to Login
          </a>
        </div>
      </div>
    </div>
  );
};

export default ApprovalPendingPage;
