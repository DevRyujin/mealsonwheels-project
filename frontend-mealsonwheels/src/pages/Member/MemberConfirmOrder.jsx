import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance';

const ConfirmOrderPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    address: '',
    phone: '',
    caregiverName: '',
    caregiverRelation: '',
    menuName: '',
    mealType: '',
    preparedBy: '',
    deliveredBy: '',
    menuPlan: '',
  });
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const validate = () => {
    const newErrors = {};
    for (const key in formData) {
      if (!formData[key]) newErrors[key] = 'This field is required';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;
    try {
      await axiosInstance.post('/api/orders', formData);
      navigate('/member/currentOrder');
    } catch (err) {
      console.error('Failed to confirm order:', err);
      alert('Failed to submit the order. Please try again.');
    }
  };

  return (
    <div className="min-h-screen flex flex-col">
      <main className="flex-grow p-6">
        <h2 className="text-left text-3xl font-semibold py-6 rounded mb-4">Confirm your order here!</h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-10">
          {/* Meal Card Placeholder */}
          <div className="bg-white border rounded-xl shadow overflow-hidden flex items-center justify-center p-8 text-gray-500">
            <p className="text-center text-lg font-medium">Your selected meal will be displayed here.</p>
          </div>

          {/* Order Form */}
          <form onSubmit={handleSubmit} className="space-y-6">
            <fieldset className="border border-gray-300 p-4 rounded">
              <legend className="text-lg font-semibold">Your Details</legend>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                {['fullName','email','address','phone','caregiverName','caregiverRelation'].map(field => (
                  <div key={field} className="flex flex-col">
                    <input
                      name={field}
                      placeholder={field.replace(/([A-Z])/g, ' $1')}
                      className={`border p-2 rounded w-full ${errors[field] ? 'border-red-500' : ''}`}
                      value={formData[field]}
                      onChange={handleChange}
                    />
                    {errors[field] && <span className="text-red-500 text-sm mt-1">{errors[field]}</span>}
                  </div>
                ))}
              </div>
            </fieldset>

            <fieldset className="border border-gray-300 p-4 rounded">
              <legend className="text-lg font-semibold">Menu to order</legend>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
                {['menuName','mealType','preparedBy','deliveredBy','menuPlan'].map(field => (
                  <div key={field} className="flex flex-col col-span-1 md:col-span-1">
                    <input
                      name={field}
                      placeholder={field.replace(/([A-Z])/g, ' $1')}
                      className={`border p-2 rounded w-full ${errors[field] ? 'border-red-500' : ''}`}
                      value={formData[field]}
                      onChange={handleChange}
                    />
                    {errors[field] && <span className="text-red-500 text-sm mt-1">{errors[field]}</span>}
                  </div>
                ))}
              </div>
            </fieldset>

            <div className="text-center">
              <button
                type="submit"
                className="inline-block mt-4 px-6 py-2 border border-black rounded hover:bg-gray-200 transition"
              >
                Confirm Order
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default ConfirmOrderPage;
