import React from "react";

export default function VolunteerStatus() {
  return (
    <div className="min-h-screen bg-gray-100">

      {/* Main Content */}
      <main className="px-10 py-6">
        <h1 className="text-xl font-bold text-center mb-8">Delivery Status - Volunteer</h1>

        {/* Active Deliveries */}
        <section className="mb-12">
          <h2 className="font-semibold mb-2">Active Deliveries</h2>
          <p className="text-sm mb-4">Manage your ongoing deliveries and update their status</p>

          <div className="overflow-x-auto">
            <table className="min-w-full bg-white shadow-md rounded">
              <thead className="bg-gray-200">
                <tr className="text-left text-sm">
                  <th className="py-2 px-4">No.</th>
                  <th className="py-2 px-4">Name</th>
                  <th className="py-2 px-4">Meal Name</th>
                  <th className="py-2 px-4">Restaurant</th>
                  <th className="py-2 px-4">Restaurant Address</th>
                  <th className="py-2 px-4">Order Date</th>
                  <th className="py-2 px-4">Code</th>
                  <th className="py-2 px-4">Start Delivery Time</th>
                  <th className="py-2 px-4">Delivery Status</th>
                </tr>
              </thead>
              <tbody>
                <tr className="border-t">
                  <td className="py-2 px-4">1</td>
                  <td className="py-2 px-4">Andrei Santos</td>
                  <td className="py-2 px-4">Pad Thai</td>
                  <td className="py-2 px-4">Red House</td>
                  <td className="py-2 px-4">No 34 at Hospital Road, long road, Lagos state</td>
                  <td className="py-2 px-4">2025-06-09</td>
                  <td className="py-2 px-4">Good to Pickup (Ready)</td>
                  <td className="py-2 px-4">2025-06-09</td>
                  <td className="py-2 px-4 text-yellow-600 font-medium">In Progress</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>

        {/* Completed Deliveries */}
        <section>
          <h2 className="font-semibold mb-2">Completed Deliveries</h2>
          <p className="text-sm mb-4">View your successfully completed deliveries</p>

          <div className="overflow-x-auto">
            <table className="min-w-full bg-white shadow-md rounded">
              <thead className="bg-gray-200">
                <tr className="text-left text-sm">
                  <th className="py-2 px-4">No.</th>
                  <th className="py-2 px-4">Name</th>
                  <th className="py-2 px-4">Meal Name</th>
                  <th className="py-2 px-4">Restaurant</th>
                  <th className="py-2 px-4">Order Date</th>
                  <th className="py-2 px-4">Completion Status</th>
                </tr>
              </thead>
              <tbody>
                <tr className="border-t">
                  <td className="py-2 px-4">1</td>
                  <td className="py-2 px-4">Andrei Santos</td>
                  <td className="py-2 px-4">Pad Thai</td>
                  <td className="py-2 px-4">Goodies Restaurant</td>
                  <td className="py-2 px-4">2025-06-09</td>
                  <td className="py-2 px-4 text-green-600 font-medium">Successful Delivery</td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
      </main>

      {/* Footer */}
      <footer className="bg-[#be1e2d] text-white py-6 px-10 mt-12">
        <div className="grid grid-cols-3 text-sm gap-4">
          <div>
            <h4 className="font-bold mb-2">ABOUT US</h4>
            <p>Home</p>
            <p>About</p>
            <p>Services</p>
            <p>FAQ</p>
          </div>
          <div>
            <h4 className="font-bold mb-2">SERVICES</h4>
            <p>Delivery</p>
            <p>Packaging</p>
          </div>
          <div>
            <h4 className="font-bold mb-2">CONTACT</h4>
            <p>Mon - Sun</p>
            <p>8:00am - 8:00pm</p>
            <p>meals@wheels.com</p>
            <p>0800 000 0000</p>
          </div>
        </div>
      </footer>
    </div>
  );
}