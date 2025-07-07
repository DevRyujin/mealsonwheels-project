import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const DonateAmountPage = () => {
  const navigate = useNavigate();
  const presetAmounts = [1000, 500, 300, 200, 100];
  const [selectedAmount, setSelectedAmount] = useState(null);
  const [customAmount, setCustomAmount] = useState('');

  const handleSelect = (amount) => {
    setSelectedAmount(amount);
    setCustomAmount('');
  };

  const handleCustomChange = (e) => {
    setCustomAmount(e.target.value);
    setSelectedAmount(null);
  };

  const handleContinue = () => {
    const amountToStore = selectedAmount || parseFloat(customAmount);
    if (!amountToStore || amountToStore <= 0) {
      alert('Please enter or select a valid donation amount.');
      return;
    }

    localStorage.setItem('donationAmount', amountToStore);
    navigate('/donate/billing');
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-r from-orange-100 to-red-400 px-6 py-12">
      <div className="bg-white shadow-lg rounded-2xl p-8 w-full max-w-md text-center">
        <h1 className="text-2xl font-bold text-red-700 mb-6">Select Donation Amount</h1>

        <div className="grid grid-cols-2 gap-4 mb-6">
          {presetAmounts.map((amount) => (
            <button
              key={amount}
              onClick={() => handleSelect(amount)}
              className={`px-4 py-3 rounded-xl border-2 font-semibold transition-all transform active:scale-95 ${
                selectedAmount === amount
                  ? 'bg-red-500 text-white border-red-600'
                  : 'border-gray-300 bg-white hover:bg-red-100'
              }`}
            >
              ₱{amount.toLocaleString()}
            </button>
          ))}
        </div>

        <div className="mb-6">
          <input
            type="number"
            value={customAmount}
            onChange={handleCustomChange}
            placeholder="₱ Enter custom amount"
            className="w-full px-4 py-3 border-2 border-gray-300 rounded-xl focus:outline-none focus:border-red-500"
          />
        </div>

        <button
          onClick={handleContinue}
          className="w-full bg-gradient-to-r from-orange-300 to-red-600 text-white font-semibold px-6 py-3 rounded-xl shadow hover:brightness-110 transform transition-transform active:scale-95"
        >
          Continue to Details
        </button>
      </div>
    </div>
  );
};

export default DonateAmountPage;
