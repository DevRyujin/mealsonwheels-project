import React from 'react';
import { NavLink } from "react-router-dom";

const MealDetailsPage = () => {
  return (
    <div className="min-h-screen flex flex-col">

      {/* Main Content */}
      <main className="flex-grow p-6 text-center">
        <h1 className="text-2xl font-bold mb-2">Pad Thai</h1>
        <div className="bg-gray-300 p-2 rounded mb-4 text-sm">
          This Meal is available today and will be provided based on your distance
        </div>

        <div className="flex flex-col items-center">
          <img
            src="https://upload.wikimedia.org/wikipedia/commons/4/4b/Pad_Thai_kung_Chang_Khon.jpg"
            alt="Pad Thai"
            className="w-60 h-40 object-cover rounded shadow mb-4"
          />
          <h2 className="text-lg font-semibold mb-1">Description</h2>
          <p className="max-w-md text-sm mb-4">
            Pad Thai is a classic Thai stir-fried noodle dish made with rice noodles, eggs, tofu or shrimp,
            bean sprouts, and peanuts, all tossed in a tangy-sweet tamarind-based sauce.
          </p>

          <div className="flex justify-center gap-4 mb-4">
            <div className="bg-gray-300 p-4 rounded w-48">
              <strong>Time Availability</strong>
              <p>This Meal is available today</p>
            </div>
            <div className="bg-gray-300 p-4 rounded w-48">
              <strong>Meal Type</strong>
              <p>Hot</p>
            </div>
          </div>

          <NavLink
            to="/food-safety"
            className="inline-block border border-gray-500 rounded px-4 py-1 text-sm mb-3 transform transition-transform duration-150 active:scale-95"
          >
            Food Safety
          </NavLink>


          <br />

          <NavLink
            to="/member/confirm-order"
            className="inline-block border border-black rounded px-6 py-2 font-semibold hover:bg-gray-200 transform transition-transform duration-150 active:scale-95"
          >
            Order now
          </NavLink>
        </div>
      </main>
    </div>
  );
};

export default MealDetailsPage;
