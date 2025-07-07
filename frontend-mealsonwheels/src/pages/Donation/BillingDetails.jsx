import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axiosInstance from '../../api/axiosInstance';

export default function BillingDetails() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: '',
    address: '',
    cityStateCountry: '',
    email: '',
    phone: '',
  });

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserData = async () => {
      const token = localStorage.getItem('token');
      if (!token) {
        setLoading(false);
        return;
      }

      try {
        const response = await axiosInstance.get('/users/me');
        const user = response.data;

        setFormData({
          fullName: user.fullName || '',
          address: user.address || '',
          cityStateCountry: user.cityStateCountry || '',
          email: user.email || '',
          phone: user.phone || '',
        });
      } catch (error) {
        console.error('Failed to fetch user billing info:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
    setErrors({ ...errors, [e.target.name]: '' });
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.fullName.trim()) newErrors.fullName = 'Full Name is required';
    if (!formData.address.trim()) newErrors.address = 'Address is required';
    if (!formData.cityStateCountry.trim()) newErrors.cityStateCountry = 'City/State/Country is required';
    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Invalid email format';
    }
    if (!formData.phone.trim()) newErrors.phone = 'Phone number is required';

    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();

    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      console.log('Billing Info Submitted:', formData);

      // Save billing info to localStorage (optional)
      localStorage.setItem('billingInfo', JSON.stringify(formData));
      localStorage.setItem('donorName', formData.fullName);

      // ✅ Redirect to payment page
      navigate('/donate/payment');
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen flex justify-center items-center text-xl font-semibold">
        Loading billing info...
      </div>
    );
  }

  return (
    <div className="min-h-screen flex flex-col justify-center items-center bg-gradient-to-r from-orange-100 to-red-400">
      <div className="w-full max-w-xl bg-white p-8 rounded-xl shadow-md my-10">
        <h1 className="text-3xl font-bold text-center text-red-800 mb-6">Billing Details</h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          {['fullName', 'address', 'cityStateCountry', 'email', 'phone'].map((field) => (
            <div key={field}>
              <input
                type={field === 'email' ? 'email' : field === 'phone' ? 'tel' : 'text'}
                name={field}
                placeholder={field
                  .replace(/([A-Z])/g, ' $1')
                  .replace(/^./, (str) => str.toUpperCase())}
                value={formData[field]}
                onChange={handleChange}
                className="w-full px-4 py-3 border border-gray-300 rounded-xl shadow-sm focus:outline-none focus:border-red-500"
              />
              {errors[field] && <p className="text-red-500 text-sm mt-1">{errors[field]}</p>}
            </div>
          ))}

          <button
            type="submit"
            className="w-full bg-gradient-to-r from-orange-300 to-red-600 text-white font-semibold py-3 rounded-xl hover:brightness-110 transform transition-transform active:scale-95"
          >
            Continue to Payment
          </button>
        </form>
      </div>
    </div>
  );
}
