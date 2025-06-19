// src/pages/RegisterVolunteer.jsx
import React, { useState } from 'react';

export default function RegisterVolunteer() {
  const [form, setForm] = useState({
    name: '',
    email: '',
    phone: '',
    password: '',
    role: 'Volunteer',
    serviceType: 'Kitchen',
    duration: '',
    days: [],
  });

  const daysOfWeek = [
    'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'
  ];

  const toggleDay = (day) => {
    setForm((prev) => ({
      ...prev,
      days: prev.days.includes(day)
        ? prev.days.filter((d) => d !== day)
        : [...prev.days, day],
    }));
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(form);
    alert('Volunteer registered!');
  };

  return (
    <div className="min-h-screen bg-white px-4 py-10 md:px-0 flex justify-center items-start">
      <form
        onSubmit={handleSubmit}
        className="bg-gray-200 w-full max-w-md p-8 rounded shadow"
      >
        <h2 className="text-2xl font-semibold text-center mb-6">Create Account</h2>

        <label className="block mb-1">Name:</label>
        <input
          type="text"
          name="name"
          value={form.name}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border"
          required
        />

        <label className="block mb-1">Email Address:</label>
        <input
          type="email"
          name="email"
          value={form.email}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border"
          required
        />

        <label className="block mb-1">Phone number:</label>
        <input
          type="tel"
          name="phone"
          value={form.phone}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border"
          required
        />

        <label className="block mb-1">Password:</label>
        <input
          type="password"
          name="password"
          value={form.password}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border"
          required
        />

        <label className="block mb-1">Choose your Role:</label>
        <select
          name="role"
          value={form.role}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border bg-white"
        >
          <option value="Volunteer">Volunteer</option>
          <option value="Member">Member</option>
          <option value="Partner">Partner</option>
        </select>

        <label className="block mb-1">Type of services:</label>
        <select
          name="serviceType"
          value={form.serviceType}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border bg-white"
        >
          <option value="Kitchen">Kitchen</option>
          <option value="Delivery">Delivery</option>
          <option value="Packaging">Packaging</option>
        </select>

        <label className="block mb-1">Volunteer Duration:</label>
        <input
          type="date"
          name="duration"
          value={form.duration}
          onChange={handleChange}
          className="w-full mb-4 px-3 py-2 border"
        />

        <label className="block mb-2">Available Days:</label>
        <div className="grid grid-cols-2 gap-2 mb-6">
          {daysOfWeek.map((day) => (
            <label key={day} className="flex items-center gap-2">
              <input
                type="checkbox"
                checked={form.days.includes(day)}
                onChange={() => toggleDay(day)}
              />
              {day}
            </label>
          ))}
        </div>

        <div className="flex justify-between gap-2">
          <button
            type="submit"
            className="bg-black text-white px-4 py-2 rounded hover:bg-gray-800"
          >
            Create
          </button>
          <button
            type="button"
            className="px-4 py-2 border rounded hover:bg-gray-100"
            onClick={() => window.history.back()}
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}
