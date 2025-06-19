import React from 'react';

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

          <button className="border border-gray-500 rounded px-4 py-1 text-sm mb-3">Food Safety</button>
          <br />
          <button className="border border-black rounded px-6 py-2 font-semibold hover:bg-gray-200">Order now</button>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-red-700 text-white p-4 text-sm grid grid-cols-3 gap-4 text-left">
        <div>
          <div className="font-semibold mb-2">ABOUT US</div>
          <p>Price</p>
          <p>Menu</p>
          <p>Home</p>
        </div>
        <div>
          <div className="font-semibold mb-2">SERVICES</div>
          <p>Delivery</p>
          <p>Packaging</p>
        </div>
        <div>
          <div className="font-semibold mb-2">SCHEDULE</div>
          <p>Hot Meals</p>
          <p>Mon–Fri</p>
          <p>9:00 am – 8:00 pm</p>
          <br />
          <p>Frozen Meals</p>
          <p>Sat–Sun</p>
          <p>Delivered within 10km</p>
        </div>
      </footer>
    </div>
  );
};

export default MealDetailsPage;
