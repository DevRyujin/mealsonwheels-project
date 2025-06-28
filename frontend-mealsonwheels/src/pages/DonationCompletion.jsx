import React from 'react';

const DonationCompletePage = () => {
  return (
    <div className="min-h-screen flex flex-col justify-between">
      <main className="flex-grow p-6 flex flex-col items-center text-center">
        <img src="/images/donation-graphic.png" alt="Donation Graphic" className="w-40 mb-6" />
        <h1 className="text-2xl font-bold mb-4">Donation Complete</h1>
        <div className="mb-4">
          <img src="/images/checkmark.png" alt="Checkmark" className="w-12 mx-auto mb-2" />
          <p className="text-lg font-medium">Thank you for your donation!</p>
          <p>Your contribution has been received successfully.</p>
        </div>
        <button
          onClick={() => window.location.href = '/'}
          className="bg-gray-700 text-white py-2 px-4 rounded hover:bg-gray-800"
        >
          Return Home
        </button>
      </main>

      <footer className="bg-red-600 text-white text-center p-4 text-sm">
        <p>© Meals on Wheels | About Us | Services | Schedule</p>
      </footer>
    </div>
  );
};

export default DonationCompletePage;
