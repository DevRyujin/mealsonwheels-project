import React from 'react';

const MemberDashboard = () => {
  return (
    <div className="min-h-screen flex flex-col">

      {/* Sub Navbar */}
      <nav className="bg-blue-900 text-white flex flex-wrap justify-between items-center px-6 py-3 text-sm">
        <div className="flex gap-4">
          <a href="#" className="hover:underline">Dashboard</a>
          <a href="#" className="hover:underline">Orders</a>
          <a href="#" className="hover:underline">Menu</a>
          <a href="#" className="hover:underline">Delivery History</a>
          <a href="#" className="hover:underline">Contact</a>
        </div>
        <div className="font-semibold">Andrei Santos <span className="text-blue-300 ml-2">MEMBER</span></div>
      </nav>

      {/* Hero Section */}
      <section className="bg-cover bg-center text-white text-center py-24 px-4" style={{ backgroundImage: " " }}>
        <h1 className="text-3xl font-bold mb-2">HEALTHY MEALS FROM THE COMFORT OF HOME</h1>
        <p className="text-lg">Enjoy flavorful, well-balanced meals without the hassle</p>
      </section>

      {/* Dashboard Cards */}
      <section className="bg-white py-10 px-6 grid md:grid-cols-3 gap-6 text-center">
        <div className="bg-gray-100 p-6 rounded shadow-md">
          <h2 className="text-xl font-semibold mb-2">Your Meal Dashboard</h2>
          <p>Track your meal deliveries, update dietary preferences, and stay on top of your nutrition</p>
        </div>
        <div className="bg-gray-100 p-6 rounded shadow-md">
          <h2 className="text-xl font-semibold mb-2">View Menu</h2>
          <p>Browse our section of meals</p>
        </div>
        <div className="bg-gray-100 p-6 rounded shadow-md">
          <h2 className="text-xl font-semibold mb-2">My Orders</h2>
          <p>View your order history</p>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-red-700 text-white px-6 py-8 grid md:grid-cols-3 gap-6 text-sm">
        <div>
          <h4 className="font-semibold mb-2">ABOUT US</h4>
          <p>Price</p>
          <p>Menu</p>
          <p>Home</p>
        </div>
        <div>
          <h4 className="font-semibold mb-2">SERVICES</h4>
          <p>Delivery</p>
          <p>Packaging</p>
        </div>
        <div>
          <h4 className="font-semibold mb-2">SCHEDULE</h4>
          <p>Hot Meals<br />Mon–Fri<br />9:00 am – 8:00 pm</p>
          <p className="mt-2">Frozen Meals<br />Sat–Sun<br />Delivered within 10km</p>
        </div>
      </footer>
    </div>
  );
};

export default MemberDashboard;