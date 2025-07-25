import React, { useEffect, useState } from "react";
import { deliveryService } from "../../services/deliveryService";
import { CheckCircle, Clock, Truck } from "lucide-react";

export default function DeliveryStatus() {
  const [activeDeliveries, setActiveDeliveries] = useState([]);
  const [completedDeliveries, setCompletedDeliveries] = useState([]);
  const [outForDelivery, setOutForDelivery] = useState([]);


  const refreshDeliveries = async () => {
    try {
      const allPending = await deliveryService.getActiveDeliveries(); // includes ASSIGNED + IN_PROGRESS
      const completed = await deliveryService.getCompletedDeliveries();

      setActiveDeliveries(allPending.filter(o => o.status === "Assigned"));
      setOutForDelivery(allPending.filter(o => o.status === "In Progress"));

      setCompletedDeliveries(completed);
    } catch (error) {
      console.error("Error fetching deliveries", error);
    }
  };


  useEffect(() => {
    refreshDeliveries();
  }, []);

  useEffect(() => {
    console.log("🔍 Active deliveries:", activeDeliveries);
    console.log("✅ Completed deliveries:", completedDeliveries);
  }, [activeDeliveries, completedDeliveries]);

  const handleStartDelivery = async (id) => {
    try {
      await deliveryService.startDelivery(id);
      await refreshDeliveries();
    } catch (error) {
      console.error("Error starting delivery", error);
    }
  };

  const handleCompleteDelivery = async (id) => {
    try {
      await deliveryService.completeDelivery(id);
      await refreshDeliveries();
    } catch (error) {
      console.error("Error completing delivery", error);
    }
  };

  const renderStatusBadge = (status) => {
    switch (status) {
      case "Pending":
        return (
          <span className="inline-flex items-center px-3 py-1 rounded-full bg-yellow-100 text-yellow-700 text-xs font-medium">
            <Clock className="w-3 h-3 mr-1" />
            Pending
          </span>
        );
      case "In Progress":
        return (
          <span className="inline-flex items-center px-3 py-1 rounded-full bg-blue-100 text-blue-700 text-xs font-medium">
            <Truck className="w-3 h-3 mr-1" />
            In Progress
          </span>
        );
      case "Completed":
        return (
          <span className="inline-flex items-center px-3 py-1 rounded-full bg-green-100 text-green-700 text-xs font-medium">
            <CheckCircle className="w-3 h-3 mr-1" />
            Completed
          </span>
        );
      case "Assigned":
        return (
          <span className="inline-flex items-center px-3 py-1 rounded-full bg-orange-100 text-orange-700 text-xs font-medium">
            <Clock className="w-3 h-3 mr-1" />
            Assigned
          </span>
        );

      default:
        return status;
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-orange-100 to-red-100 py-12 px-4">
      <div className="max-w-7xl mx-auto space-y-12">
        <h1 className="text-3xl font-bold text-center text-gray-900">📦 Delivery Dashboard 📦</h1>

        {/* Active Deliveries */}
        <section className="bg-white rounded-2xl shadow-lg p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold text-gray-800 flex items-center gap-2">
              <Truck className="w-5 h-5 text-orange-500" /> Active Deliveries
            </h2>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left border-separate border-spacing-y-2">
              <thead className="text-gray-600">
                <tr>
                  <th>No.</th>
                  <th>Name</th>
                  <th>Meal</th>
                  <th>Delivery Address</th>
                  <th>Restaurant</th>
                  <th>Pickup Address</th>
                  <th>Order Date</th>
                  <th>Code</th>
                  <th>Start Time</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {activeDeliveries.length > 0 ? (
                  activeDeliveries.map((delivery, index) => (
                    <tr key={delivery.id} className="bg-white hover:shadow rounded-xl transition">
                      <td className="px-3 py-2">{index + 1}</td>
                      <td className="px-3 py-2">{delivery.memberName}</td>
                      <td className="px-3 py-2">{delivery.mealName}</td>
                      <td className="px-3 py-2">{delivery.memberAddress}</td>
                      <td className="px-3 py-2">{delivery.restaurant}</td>
                      <td className="px-3 py-2">{delivery.restaurantAddress}</td>
                      <td className="px-3 py-2">{delivery.orderDate}</td>
                      <td className="px-3 py-2">{delivery.code}</td>
                      <td className="px-3 py-2 text-sm text-gray-500">{delivery.startDeliveryTime || "--"}</td>
                      <td className="px-3 py-2">{renderStatusBadge(delivery.status)}</td>
                      <td className="px-3 py-2 space-x-2">
                        {delivery.status === "Assigned" && (
                        <button
                          onClick={() => handleStartDelivery(delivery.id)}
                          className="text-sm bg-orange-500 hover:bg-orange-600 text-white px-4 py-1 rounded-xl transition"
                        >
                          Start
                        </button>
                      )}
                      {delivery.status === "In Progress" && (
                        <button
                          onClick={() => handleCompleteDelivery(delivery.id)}
                          className="text-sm bg-green-500 hover:bg-green-600 text-white px-4 py-1 rounded-xl transition"
                        >
                          Complete
                        </button>
                      )}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="10" className="text-center py-6 text-gray-500">
                      No active deliveries found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </section>

        {/* Out for Delivery */}
        <section className="bg-white rounded-2xl shadow-lg p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold text-gray-800 flex items-center gap-2">
              <Truck className="w-5 h-5 text-blue-500" /> Out for Delivery
            </h2>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left border-separate border-spacing-y-2">
              <thead className="text-gray-600">
                <tr>
                  <th>No.</th>
                  <th>Name</th>
                  <th>Meal</th>
                  <th>Delivery Address</th>
                  <th>Restaurant</th>
                  <th>Order Date</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {outForDelivery.length > 0 ? (
                  outForDelivery.map((delivery, index) => (
                    <tr key={delivery.id} className="bg-white hover:shadow rounded-xl transition">
                      <td className="px-3 py-2">{index + 1}</td>
                      <td className="px-3 py-2">{delivery.memberName}</td>
                      <td className="px-3 py-2">{delivery.mealName}</td>
                      <td className="px-3 py-2">{delivery.memberAddress}</td>
                      <td className="px-3 py-2">{delivery.restaurant}</td>
                      <td className="px-3 py-2">{delivery.orderDate}</td>
                      <td className="px-3 py-2">{renderStatusBadge(delivery.status)}</td>
                      <td className="px-3 py-2">
                        <button
                          onClick={() => handleCompleteDelivery(delivery.id)}
                          className="text-sm bg-green-500 hover:bg-green-600 text-white px-4 py-1 rounded-xl transition"
                        >
                          Complete
                        </button>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="8" className="text-center py-6 text-gray-500">
                      No out-for-delivery orders.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </section>

        {/* Completed Deliveries */}
        <section className="bg-white rounded-2xl shadow-lg p-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold text-gray-800 flex items-center gap-2">
              <CheckCircle className="w-5 h-5 text-green-500" /> Completed Deliveries
            </h2>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-sm text-left border-separate border-spacing-y-2">
              <thead className="text-gray-600">
                <tr>
                  <th>No.</th>
                  <th>Name</th>
                  <th>Meal</th>
                  <th>Restaurant</th>
                  <th>Order Date</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {completedDeliveries.length > 0 ? (
                  completedDeliveries.map((delivery, index) => (
                    <tr key={delivery.id} className="bg-white hover:shadow rounded-xl transition">
                      <td className="px-3 py-2">{index + 1}</td>
                      <td className="px-3 py-2">{delivery.memberName}</td>
                      <td className="px-3 py-2">{delivery.mealName}</td>
                      <td className="px-3 py-2">{delivery.restaurant}</td>
                      <td className="px-3 py-2">{delivery.orderDate}</td>
                      <td className="px-3 py-2">{renderStatusBadge(delivery.status)}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="6" className="text-center py-6 text-gray-500">
                      No completed deliveries found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </section>
      </div>
    </div>
  );
}
