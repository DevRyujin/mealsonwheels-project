import React from 'react';
import { NavLink } from 'react-router-dom';

const ConfirmOrderPage = () => {
  return (
    <div className="min-h-screen flex flex-col">

      {/* Main Content */}
      <main className="flex-grow p-6">
        <div className="text-center">
          <h1 className="text-2xl font-bold mb-2">Pad Thai</h1>
          <div className="bg-gray-300 p-2 rounded mb-4 text-sm">
            This Meal is available today and will be provided based on your distance
          </div>

          <div className="flex flex-col items-center mb-6">
            <img
              src="https://upload.wikimedia.org/wikipedia/commons/4/4b/Pad_Thai_kung_Chang_Khon.jpg"
              alt="Pad Thai"
              className="w-60 h-40 object-cover rounded shadow mb-4"
            />
            <h2 className="text-lg font-semibold mb-1">Description</h2>
            <p className="max-w-md text-sm">
              Pad Thai is a classic Thai stir-fried noodle dish made with rice noodles, eggs, tofu or shrimp,
              bean sprouts, and peanuts, all tossed in a tangy-sweet tamarind-based sauce.
            </p>
          </div>
        </div>

        {/* Confirm Order Title */}
        <h2 className="text-center bg-gray-300 text-lg font-semibold py-2 rounded mb-4">Confirm your order</h2>

        {/* Order Form */}
        <form className="max-w-4xl mx-auto space-y-6">
          <fieldset className="border border-gray-300 p-4 rounded">
            <legend className="text-lg font-semibold">Your Details</legend>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
              <input type="text" placeholder="Full name" className="border p-2 rounded w-full" />
              <input type="email" placeholder="Email" className="border p-2 rounded w-full" />
              <input type="text" placeholder="Address" className="border p-2 rounded w-full" />
              <input type="text" placeholder="Phone number" className="border p-2 rounded w-full" />
              <input type="text" placeholder="Caregiver name" className="border p-2 rounded w-full" />
              <input type="text" placeholder="Caregiver relation" className="border p-2 rounded w-full" />
            </div>
          </fieldset>

          <fieldset className="border border-gray-300 p-4 rounded">
            <legend className="text-lg font-semibold">Menu to order</legend>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4">
              <input type="text" placeholder="Menu name" value="Pad Thai" readOnly className="border p-2 rounded w-full" />
              <input type="text" placeholder="Meal type" value="Hot" readOnly className="border p-2 rounded w-full" />
              <input type="text" placeholder="Menu prepared by" className="border p-2 rounded w-full" />
              <input type="text" placeholder="Delivered by" className="border p-2 rounded w-full" />
              <input type="text" placeholder="Menu Plan" className="border p-2 rounded w-full col-span-2" />
            </div>
          </fieldset>

          <div className="text-center">
            <NavLink
              to="/member/currentOrder"
              className="inline-block mt-4 px-6 py-2 border border-black rounded hover:bg-gray-200 transition"
            >
              Confirm Order
            </NavLink>
          </div>
        </form>
      </main>
    </div>
  );
};

export default ConfirmOrderPage;
