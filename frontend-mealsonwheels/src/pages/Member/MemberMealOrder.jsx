import React, { useEffect, useState } from 'react';
import { NavLink } from "react-router-dom";
import axiosInstance from '../../api/axiosInstance';

const MealDetailsPage = () => {
  const [meals, setMeals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchMeals = async () => {
      try {
        const response = await axiosInstance.get("/api/menus");
        setMeals(response.data);
      } catch (err) {
        console.error("Failed to fetch meals:", err);
        setError("No current meal available. Please come back again later. Thank you!");
      } finally {
        setLoading(false);
      }
    };

    fetchMeals();
  }, []);

  return (
    <div className="min-h-screen flex flex-col bg-white">
      <main className="flex-grow p-6">
        <h1 className="text-2xl font-bold text-center mb-6">ORDER HERE</h1>

        <p className="bg-gray-100 p-6 rounded text-2sm text-left font-sans mb-8">
          These meals are available today and will be provided based on your distance.
        </p>

        {loading && <p className="text-center text-gray-500">Loading meals...</p>}

        {error && (
          <div className="text-center">
            <p className="text-gray-900">{error}</p>
            <img
              src="/images/bow.gif"
              alt="bow"
              className="w-full max-w-xs mx-auto my-4"
            />
          </div>
        )}

        {/* Meal Cards */}
        {!loading && !error && (
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 max-w-6xl mx-auto">
            {meals.map((meal) => (
              <div
                key={meal.id}
                className="bg-white border rounded-xl shadow hover:shadow-md transition overflow-hidden"
              >
                <img
                  src={meal.imageUrl || "/images/placeholder.jpg"}
                  alt={meal.title}
                  className="w-full h-52 object-cover"
                />
                <div className="p-5">
                  <h2 className="text-xl font-bold mb-1">{meal.title}</h2>
                  <p className="text-sm text-gray-600 mb-4">{meal.description}</p>

                  <div className="flex flex-col sm:flex-row justify-between gap-4 mb-4">
                    <div className="text-sm text-gray-700">
                      <strong>Time:</strong> {meal.availability || "Available today"}
                    </div>
                    <div className="text-sm text-gray-700">
                      <strong>Type:</strong> {meal.type || "Hot"}
                    </div>
                  </div>

                  <div className="flex gap-4">
                    <NavLink
                      to="/food-safety"
                      className="w-full text-center border border-gray-400 px-4 py-2 rounded hover:bg-gray-100 text-sm"
                    >
                      Food Safety
                    </NavLink>
                    <NavLink
                      to="/member/confirm-order"
                      className="w-full text-center border border-gray-400 px-4 py-2 rounded hover:bg-gray-100 text-sm"
                    >
                      Order Now
                    </NavLink>
                  </div>
                </div>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
};

export default MealDetailsPage;
