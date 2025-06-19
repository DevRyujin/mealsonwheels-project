import React from "react";

export default function DeliveryStatus() {
  return (
    <div className="min-h-screen flex flex-col">

      {/* Main Content */}
      <main className="flex-grow bg-gray-100 px-4 py-10">
        <h1 className="text-2xl font-bold text-center mb-8">Delivery Status - Volunteer</h1>

        {/* Active Deliveries */}
        <section className="bg-white shadow-md p-6 rounded-lg max-w-6xl mx-auto mb-8">
          <h2 className="text-lg font-bold mb-4">Active Deliveries</h2>
          <p className="text-sm text-gray-600 mb-4">Manage your ongoing deliveries and update their status</p>

          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left">
              <thead className="bg-gray-200">
                <tr>
                  <th className="px-4 py-2">NO.</th>
                  <th className="px-4 py-2">MEMBER NAME</th>
                  <th className="px-4 py-2">MEAL NAME</th>
                  <th className="px-4 py-2">RESTAURANT ADDRESS</th>
                  <th className="px-4 py-2">ORDER DATE</th>
                  <th className="px-4 py-2">ORDER TIME</th>
                  <th className="px-4 py-2">START DELIVERY TIME</th>
                  <th className="px-4 py-2">ACTION</th>
                </tr>
              </thead>
              <tbody>
                <tr className="border-t">
                  <td className="px-4 py-2">1</td>
                  <td className="px-4 py-2">Arwind Santos</td>
                  <td className="px-4 py-2">Pad Thai</td>
                  <td className="px-4 py-2">Syudad Eatery, Brillantes</td>
                  <td className="px-4 py-2">2023-06-09</td>
                  <td className="px-4 py-2">11:45 am</td>
                  <td className="px-4 py-2">--</td>
                  <td className="px-4 py-2">
                    <button className="bg-gray-800 text-white px-3 py-1 rounded hover:bg-gray-700">
                      Start
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>

        {/* Completed Deliveries */}
        <section className="bg-white shadow-md p-6 rounded-lg max-w-6xl mx-auto">
          <h2 className="text-lg font-bold mb-4">Completed Deliveries</h2>
          <p className="text-sm text-gray-600 mb-4">View your successful and concluded deliveries</p>
          <div className="text-center text-gray-500 py-10">
            <p>No completed deliveries</p>
            <p className="text-xs">You haven't completed any deliveries as of today.</p>
          </div>
        </section>
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
