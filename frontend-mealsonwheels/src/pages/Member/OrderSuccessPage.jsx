import React from 'react';
import { Link } from 'react-router-dom';

const OrderSuccessPage = () => {
  const user = JSON.parse(localStorage.getItem("user")); // assuming you store user here

  let dashboardPath = "/";

  if (user?.role === "MEMBER") {
    dashboardPath = "/member/dashboard";
  } else if (user?.role === "CAREGIVER") {
    dashboardPath = "/caregiver/dashboard";
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-white text-center p-6">
      <h1 className="text-3xl font-bold text-green-600 mb-4">🎉 Order Successful!</h1>
      <p className="text-gray-700 mb-6">Thank you for your order. We will deliver it shortly.</p>
      <Link
        to={dashboardPath}
        className="px-4 py-2 border border-black rounded hover:bg-gray-200 transition"
      >
        Back to Dashboard
      </Link>
    </div>
  );
};

export default OrderSuccessPage;
