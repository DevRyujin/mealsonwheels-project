import React, { useEffect } from "react";
import { HiCheckCircle } from "react-icons/hi";
import Confetti from "react-confetti";

const DonationCompletePage = () => {
  const donorName = localStorage.getItem("donorName") || "Valued Donor";
  const donationAmount = localStorage.getItem("donationAmount") || "0";

  const handleReturnHome = () => {
    localStorage.removeItem("donorName");
    localStorage.removeItem("donationAmount");
    localStorage.removeItem("billingInfo");
    localStorage.removeItem("paymentInfo");
    window.location.href = "/";
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      handleReturnHome();
    }, 10000);
    return () => clearTimeout(timer);
  }, []);

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-orange-100 to-red-300 relative overflow-hidden">
      <Confetti numberOfPieces={250} recycle={false} />

      {/* Confetti-like glow burst */}
      <div className="absolute w-60 h-60 bg-yellow-400 rounded-full top-[-40px] left-[-40px] blur-2xl opacity-30 animate-pulse"></div>
      <div className="absolute w-60 h-60 bg-red-400 rounded-full bottom-[-40px] right-[-40px] blur-2xl opacity-30 animate-pulse"></div>

      <main className="z-10 px-6 py-10">
        <div className="bg-white shadow-2xl rounded-2xl p-8 w-full max-w-md text-center">
          <h1 className="text-3xl font-bold text-red-700 mb-6">Donation Complete!</h1>

          <div className="mb-6">
            <p className="text-lg font-semibold text-gray-800">
              Thank you, <span className="text-red-600">{donorName}</span>!
            </p>
            <div className="text-center">
              <img
                src="/images/bow.gif"
                alt="bow"
                className="w-32 h-32 mx-auto my-4 object-contain"
              />
            </div>
            <p className="text-gray-600">
              Your contribution of{" "}
              <span className="font-medium text-red-500">
                ₱{parseFloat(donationAmount).toLocaleString()}
              </span>{" "}
              has been received successfully.
            </p>
            <p className="text-sm text-gray-500 mt-4">Redirecting in 10 seconds...</p>
          </div>

          <button
            onClick={handleReturnHome}
            className="bg-gradient-to-r from-orange-300 to-red-600 text-white font-semibold px-6 py-3 rounded-xl shadow hover:brightness-110 transform transition-transform active:scale-95"
          >
            Return Home Now
          </button>
        </div>
      </main>
    </div>
  );
};

export default DonationCompletePage;
