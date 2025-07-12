import React from 'react';
import { useNavigate } from "react-router-dom";

const MemberDashboard = () => {
  const navigate = useNavigate();

  return (
    <div className="max-w-8xl mx-auto">
      {/* Hero Section */}
      <div className="relative h-[530px] overflow-hidden">
        <img
          src="/images/MDashboardBG.png"
          alt="vegetables"
          className="absolute w-full bottom-0 h-[530px] object-cover object-bottom"
        />
        <h1 className="absolute z-10 text-white text-3xl sm:text-4xl md:text-5xl lg:text-6xl font-bold top-40 transform -translate-y-1/2 text-center px-4 w-full">
          HEALTHY MEALS FROM THE <br className="hidden sm:block" />
          COMFORT OF HOME
        </h1>
        <h2 className="absolute z-10 text-white text-lg sm:text-lg md:text-lg lg:text-2xl font-semibold top-60 transform -translate-y-1/2 text-center px-4 w-full">
          Enjoy flavorful, well-balanced meals without the hassle
        </h2>
      </div>

      {/* Dashboard Cards */}
      <section className="bg-white py-28 px-20 grid md:grid-cols-3 gap-20 text-center">
        <div>
          <h2 className="text-2xl font-semibold mb-2">Your Meal Dashboard</h2>
          <p className="font-medium">Track your meal deliveries, update dietary preferences, and stay on top of your nutrition</p>
        </div>

        <div>
          <h2 className="text-2xl font-semibold mb-2">View Menu</h2>
          <p className="font-medium mb-3">Browse our section of meals</p>
          <button
            onClick={() => navigate('/member/meal-order')}
            className="inline-block px-4 py-2 bg-indigo-900 text-white rounded hover:bg-indigo-800"
          >
            Browse Meals
          </button>
        </div>

        <div>
          <h2 className="text-2xl font-semibold mb-4">My Orders</h2>
          <p className="font-medium mb-3">View your order history</p>
          <button
            onClick={() => navigate('/member/order')}
            className="inline-block px-4 py-2 bg-indigo-900 text-white rounded hover:bg-indigo-800"
          >
            View Orders
          </button>
        </div>
      </section>
    </div>
  );
};

export default MemberDashboard;
