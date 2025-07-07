import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance';

const DonatePaymentPage = () => {
  const [formData, setFormData] = useState({
    cardHolderName: '',
    cardNumber: '',
    cardType: '',
    expMonth: '',
    expYear: '',
  });

  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: '' });
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.cardHolderName.trim()) newErrors.cardHolderName = "Cardholder's name is required";
    if (!formData.cardNumber.trim()) newErrors.cardNumber = "Credit card number is required";
    if (!formData.cardType.trim()) newErrors.cardType = "Card type is required";
    if (!formData.expMonth.trim()) newErrors.expMonth = "Expiration month is required";
    if (!formData.expYear.trim()) newErrors.expYear = "Expiration year is required";
    return newErrors;
  };

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    const billingInfo = JSON.parse(localStorage.getItem("billingInfo"));
    const donationAmount = parseFloat(localStorage.getItem("donationAmount"));

    const payload = {
      donorType: "Individual", // or “Organization”
      totalDonatedAmount: donationAmount,
      cardHolderName: formData.cardHolderName,
      cardType: formData.cardType,
      cardNumberMasked: `**** **** **** ${formData.cardNumber.slice(-4)}`,
      expiryMonth: parseInt(formData.expMonth),
      expiryYear: parseInt(formData.expYear),
      user: {
        name: billingInfo.fullName,
        email: billingInfo.email,
        phone: billingInfo.phone,
        address: billingInfo.address,
      }
    };

    try {
      await axiosInstance.post('/donor/donate', payload);
      localStorage.setItem("donorName", billingInfo.fullName);
      window.location.href = "/donate/complete";
    } catch (error) {
      console.error("Donation failed:", error);
      alert("Something went wrong while processing your donation.");
    }
  };


  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gradient-to-r from-orange-100 to-red-400">
      <div className="w-full max-w-xl bg-white p-8 rounded-xl shadow-md my-10">
        <h1 className="text-3xl font-bold text-center text-red-800 mb-6">Payment Details</h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <input
              type="text"
              name="cardHolderName"
              placeholder="Card Holder's Fullname"
              value={formData.cardHolderName}
              onChange={handleChange}
              className="w-full px-4 py-3 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:border-red-500"
            />
            {errors.cardHolderName && <p className="text-red-500 text-sm">{errors.cardHolderName}</p>}
          </div>

          <div>
            <input
              type="text"
              name="cardNumber"
              placeholder="Credit Card Number"
              value={formData.cardNumber}
              onChange={handleChange}
              className="w-full px-4 py-3 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:border-red-500"
            />
            {errors.cardNumber && <p className="text-red-500 text-sm">{errors.cardNumber}</p>}
          </div>

          <div>
            <input
              type="text"
              name="cardType"
              placeholder="Card Type (e.g., Visa)"
              value={formData.cardType}
              onChange={handleChange}
              className="w-full px-4 py-3 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:border-red-500"
            />
            {errors.cardType && <p className="text-red-500 text-sm">{errors.cardType}</p>}
          </div>

          <div className="flex gap-2">
            <div className="w-1/2">
              <input
                type="text"
                name="expMonth"
                placeholder="Expiration Month"
                value={formData.expMonth}
                onChange={handleChange}
                className="w-full px-4 py-3 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:border-red-500"
              />
              {errors.expMonth && <p className="text-red-500 text-sm">{errors.expMonth}</p>}
            </div>

            <div className="w-1/2">
              <input
                type="text"
                name="expYear"
                placeholder="Expiration Year"
                value={formData.expYear}
                onChange={handleChange}
                className="w-full px-4 py-3 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:border-red-500"
              />
              {errors.expYear && <p className="text-red-500 text-sm">{errors.expYear}</p>}
            </div>
          </div>

          <button
            type="submit"
            className="w-full bg-gradient-to-r from-orange-300 to-red-600 text-white font-semibold py-3 rounded-xl hover:brightness-110 transform transition-transform active:scale-95"
          >
            Pay Now
          </button>
        </form>
      </div>
    </div>
  );
};

export default DonatePaymentPage;
