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
    </div>
  );
}
