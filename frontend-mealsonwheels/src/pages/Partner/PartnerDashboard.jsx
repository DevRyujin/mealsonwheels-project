import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUtensils, FaClipboardList, FaStar } from 'react-icons/fa';

export default function PartnerDashboard() {
  const navigate = useNavigate();

  return (
    <div className="bg-gray-50 min-h-screen px-4 py-8 md:px-8">
      <div className="max-w-7xl mx-auto">
        {/* Welcome Header */}
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-gray-800 mb-1">Welcome, Syndicate Restaurant!</h1>
          <p className="text-gray-500">Manage your menus and delight your customers.</p>
        </div>

        {/* Dashboard Summary Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-8">
          {/* Total Menus */}
          <div className="bg-white shadow rounded p-4 text-center">
            <FaUtensils className="mx-auto text-2xl text-gray-800 mb-2" />
            <div className="text-sm text-gray-600">Total Menus</div>
            <div className="text-2xl font-semibold">0</div>
          </div>

          {/* Active Orders */}
          <div className="bg-white shadow rounded p-4 text-center">
            <FaClipboardList className="mx-auto text-2xl text-gray-800 mb-2" />
            <div className="text-sm text-gray-600">Active Orders</div>
            <div className="text-2xl font-semibold">0</div>
          </div>

          {/* Customer Rating */}
          <div className="bg-white shadow rounded p-4 text-center">
            <FaStar className="mx-auto text-2xl text-gray-800 mb-2" />
            <div className="text-sm text-gray-600">Customer Rating</div>
            <div className="text-2xl font-semibold">0/5</div>
          </div>
        </div>

        {/* Menu Section Header */}
        <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-4 gap-2">
          <h2 className="text-xl font-bold text-gray-800">Your Menus</h2>
          <div className="flex gap-2">
            <button
              className="px-5 py-2 bg-gray-800 text-white rounded hover:bg-gray-700 transition"
              onClick={() => navigate('/partner/add-menu')}
            >
              + Add Menu
            </button>
            <button
              className="px-5 py-2 border border-gray-300 text-gray-800 rounded hover:bg-gray-200 transition"
              onClick={() => navigate('/partner/partnersfoodsafety')}
            >
              Food Safety
            </button>
          </div>
        </div>

        {/* Notification Message */}
        <div className="flex items-center border-l-4 border-gray-800 p-4 shadow rounded text-sm text-gray-800">
          Menu has been created successfully.
        </div>
      </div>
    </div>
  );
}

// Reusable Summary Card Component
function SummaryCard({ icon, title, value }) {
  return (
    <div className="bg-white shadow rounded p-5 text-center hover:shadow-lg transition-shadow duration-300">
      <div className="mb-2">{icon}</div>
      <div className="text-sm text-gray-600">{title}</div>
      <div className="text-2xl font-semibold text-gray-800">{value}</div>
    </div>
  );
}
