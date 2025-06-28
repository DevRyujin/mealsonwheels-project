import React from 'react';

const DonatePaymentPage = () => {
  return (
    <div className="min-h-screen flex flex-col justify-between">
      <main className="flex-grow p-6 flex flex-col items-center text-center">
        <img src="/images/donation-graphic.png" alt="Donation Graphic" className="w-40 mb-6" />
        <h1 className="text-2xl font-bold mb-6">Billing Details</h1>

        <form className="w-full max-w-md space-y-4">
          <input
            type="text"
            placeholder="Card Holder's Fullname"
            className="w-full px-4 py-2 border rounded"
          />
          <input
            type="text"
            placeholder="Credit Card Number"
            className="w-full px-4 py-2 border rounded"
          />
          <input
            type="text"
            placeholder="Card Type (e.g., Visa)"
            className="w-full px-4 py-2 border rounded"
          />
          <div className="flex gap-2">
            <input
              type="text"
              placeholder="Expiration Month"
              className="w-1/2 px-4 py-2 border rounded"
            />
            <input
              type="text"
              placeholder="Expiration Year"
              className="w-1/2 px-4 py-2 border rounded"
            />
          </div>
          <button type="submit" className="w-full bg-red-500 text-white py-2 rounded hover:bg-red-600">
            Pay now
          </button>
        </form>
      </main>

      <footer className="bg-red-600 text-white text-center p-4 text-sm">
        <p>© Meals on Wheels | About Us | Services | Schedule</p>
      </footer>
    </div>
  );
};

export default DonatePaymentPage;
