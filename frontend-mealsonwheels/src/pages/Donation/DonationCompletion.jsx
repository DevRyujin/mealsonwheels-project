import React from 'react';
import { HiCheckCircle } from 'react-icons/hi';

const DonationCompletePage = () => {
  const donorName = localStorage.getItem("donorName") || "Valued Donor";
  const donationAmount = localStorage.getItem("donationAmount") || "an amount";

  // clear local storage
  const handleReturnHome = () => {
  localStorage.removeItem("donorName");
  localStorage.removeItem("donationAmount");
  localStorage.removeItem("billingInfo");
  localStorage.removeItem("paymentInfo");
  window.location.href = '/';
};


  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-r from-orange-100 to-red-400">
      <main className="flex-grow flex flex-col justify-center items-center px-6 py-10">
        <div className="bg-white shadow-xl rounded-2xl p-8 max-w-md w-full text-center">
          
          {/* Horizontal title with icon (optional placement) */}
          <h1 className="text-3xl font-bold text-red-700 mb-6">Donation Complete</h1>

          {/* Check icon and thank-you message */}
          <div className="mb-6">
            <div className="flex justify-center mb-4">
              <HiCheckCircle className="text-green-600 text-5xl animate-pulse" />
            </div>
            <p className="text-lg font-semibold text-gray-800">
              Thank you, <span className="text-red-600">{donorName}</span>!
            </p>
            <p className="text-gray-600">
              Your contribution of <span className="font-medium text-red-500">₱{parseFloat(donationAmount).toLocaleString()}</span> has been received successfully.
            </p>
          </div>

          <button
            onClick={handleReturnHome}
            className="bg-gradient-to-r from-orange-300 to-red-600 text-white font-semibold px-6 py-3 rounded-xl shadow hover:brightness-110 transform transition-transform active:scale-95"
          >
            Return Home
          </button>
        </div>
      </main>
    </div>
  );
};

export default DonationCompletePage;
