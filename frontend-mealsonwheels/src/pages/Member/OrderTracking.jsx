import { useEffect, useState } from 'react';
import { mealService } from '../../services/mealService';

export default function OrderTracking() {
  const [pendingOrders, setPendingOrders] = useState([]);

  useEffect(() => {
    const fetchPendingOrders = async () => {
      try {
        const data = await mealService.getMealHistory(); // pending orders only
        setPendingOrders(data);
      } catch (error) {
        console.error('Failed to fetch pending orders:', error);
      }
    };

    fetchPendingOrders();
  }, []);

  return (
    <div className="px-4 py-12 sm:px-6 lg:px-8 bg-gray-50 min-h-screen">
      <div className="max-w-6xl mx-auto p-6 bg-white rounded-xl shadow-md">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">Order Tracking</h2>

        <div className="overflow-x-auto rounded-lg border border-gray-200">
          <table className="min-w-full divide-y divide-gray-200 text-sm text-left">
            <thead className="bg-gray-100 text-gray-700">
              <tr>
                <th className="px-4 py-3">Order No.</th>
                <th className="px-4 py-3">Meal Name</th>
                <th className="px-4 py-3">Restaurant</th>
                <th className="px-4 py-3">Status</th>
                <th className="px-4 py-3">Rider</th>
              </tr>
            </thead>
            <tbody className="bg-white divide-y divide-gray-100">
              {pendingOrders.length === 0 ? (
                <tr>
                  <td colSpan="4" className="text-center text-gray-500 py-6">
                    No pending orders at the moment.
                  </td>
                </tr>
              ) : (
                pendingOrders.map((order, index) => (
                  <tr key={order.id} className="hover:bg-gray-50 transition align-middle">
                    <td className="px-4 py-2">{index + 1}</td>
                    <td className="px-4 py-2">{order.mealName}</td>
                    <td className="px-4 py-2">{order.partnerName}</td>
                    <td className="px-4 py-2 capitalize text-yellow-600 font-medium">
                      {order.status}
                    </td>
                    <td className="px-4 py-2">{order.riderName}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
