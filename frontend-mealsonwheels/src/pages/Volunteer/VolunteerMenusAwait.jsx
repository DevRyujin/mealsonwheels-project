import React from "react";

export default function MenusAwait() {
  return (
    <div className="min-h-screen flex flex-col">

      {/* Main Content */}
      <main className="flex-grow bg-gray-100 flex flex-col items-center justify-center text-center px-4 py-10">
        <h1 className="text-3xl font-bold mb-6">Delicious Menus Await</h1>
        <div className="bg-white shadow-md p-6 rounded-lg w-full max-w-2xl">
          <p className="text-lg font-semibold mb-2">No menus available at the moment</p>
          <p className="text-sm text-gray-600 mb-4">
            We’re serving up something delicious! Enjoy the bold, tangy flavors of our Pad Thai—stir-fried to perfection and packed with goodness!
          </p>
          <button className="px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700">Update me on availability</button>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-red-600 text-white px-4 py-6 mt-10">
        <div className="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-6 text-sm">
          <div>
            <div className="font-bold mb-2">MEALS ON WHEELS</div>
          </div>
          <div>
            <div className="font-bold mb-2">ABOUT US</div>
            <ul>
              <li>Price</li>
              <li>Menu</li>
              <li>Home</li>
            </ul>
          </div>
          <div>
            <div className="font-bold mb-2">SERVICES</div>
            <ul>
              <li>Delivery</li>
              <li>Packaging</li>
            </ul>
            <div className="font-bold mt-4 mb-2">SCHEDULE</div>
            <p>Hot Meals: <br /> 9:00 am - 8:00 pm</p>
            <p>Frozen Meals:<br /> 5x/week<br /> Delivered within 10km</p>
          </div>
        </div>
      </footer>
    </div>
  );
}
