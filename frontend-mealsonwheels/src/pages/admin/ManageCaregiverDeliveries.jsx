import React, { useEffect, useState } from "react";
import { saveAs } from "file-saver";
import { manageDeliveriesService } from "../../services/manageDeliveriesService";
import { toast } from "react-toastify";

export default function ManageCaregiverDeliveries() {
  const [caregivers, setCaregivers] = useState([]);
  const [riders, setRiders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [assigning, setAssigning] = useState(null);
  const [selectedRiders, setSelectedRiders] = useState({});
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  useEffect(() => {
    fetchCaregivers();
    fetchRiders();
  }, []);

  const fetchCaregivers = async () => {
    setLoading(true);
    try {
      const data = await manageDeliveriesService.getCaregiverWithOrders();
      setCaregivers(data);
    } catch (err) {
      console.error(err);
      toast.error("Error fetching caregiver orders.");
    } finally {
      setLoading(false);
    }
  };

  const fetchRiders = async () => {
    try {
      const data = await manageDeliveriesService.getApprovedRiders();
      setRiders(data);
    } catch (err) {
      console.error(err);
      toast.error("Error fetching riders.");
    }
  };

  const handleAssign = async (orderId, riderId) => {
    if (!riderId) {
      toast.warning("Please pick a rider first.");
      return;
    }

    setAssigning(orderId);
    try {
      await manageDeliveriesService.assignToRider(orderId, riderId);
      toast.success("Rider assigned successfully.");
      await fetchCaregivers(); // Refresh
    } catch (err) {
      console.error(err);
      toast.error("Assignment failed.");
    } finally {
      setAssigning(null);
    }
  };

  const exportToCSV = () => {
    if (caregivers.length === 0) return;
    const flat = caregivers.flatMap(c =>
      c.orders.map(o => ({
        caregiverName: c.caregiverName,
        orderId: o.orderId,
        meal: o.orderType,
        riderName: o.riderName || "",
        riderEmail: o.riderEmail || "",
        status: o.status,
      }))
    );

    const header = "Caregiver,Order ID,Meal,Rider Name,Rider Email,Status\n";
    const rows = flat.map(r =>
      `${r.caregiverName},${r.orderId},${r.meal},${r.riderName},${r.riderEmail},${r.status}`
    ).join("\n");

    const blob = new Blob([header + rows], { type: "text/csv" });
    saveAs(blob, "caregiver-deliveries.csv");
  };

  const flatOrders = caregivers.flatMap(c =>
    c.orders.map(o => ({ ...o, caregiverName: c.caregiverName, assistedMemberName: c.assistedMemberName || "—", }))
  );
  const totalPages = Math.ceil(flatOrders.length / itemsPerPage);
  const from = (currentPage - 1) * itemsPerPage;
  const to = Math.min(from + itemsPerPage, flatOrders.length);
  const current = flatOrders.slice(from, to);

  return (
    <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8 min-h-screen">
      <div className="text-center mt-8 mb-4">
        <h1 className="text-4xl font-bold text-gray-800">🧑‍⚕️ Caregiver Deliveries</h1>
        <p className="text-gray-500 mt-2">Manage caregiver-linked orders and rider assignments</p>
      </div>

      <div className="flex justify-between items-center bg-gray-100 p-4 rounded-lg shadow-md">
        <h2 className="text-xl font-semibold text-gray-800">Delivery Status Overview</h2>
        <button onClick={exportToCSV} className="bg-indigo-600 text-white py-2 px-4 rounded-xl hover:bg-indigo-700">
          Export CSV
        </button>
      </div>

      <div className="overflow-x-auto mt-6 rounded-lg shadow">
        <table className="min-w-full text-sm text-left text-gray-700">
          <thead className="bg-indigo-50 text-xs uppercase text-gray-700">
            <tr>
              {["#", "Caregiver", "Assisted Member", "Meal", "Order Time", "Status", "Restaurant", "Address", "Rider", "Rider Email", "Actions"].map(h => (
                <th key={h} className="px-6 py-3">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {loading
              ? <tr><td colSpan="10" className="text-center py-6">Loading...</td></tr>
              : current.length === 0
              ? <tr><td colSpan="10" className="text-center py-6">No data available</td></tr>
              : current.map((order, idx) => (
                <tr key={`${order.caregiverId}-${order.orderId}`} className="bg-white border-b hover:bg-gray-50">
                  <td className="px-6 py-4">{from + idx + 1}</td>
                  <td className="px-6 py-4">{order.caregiverName}</td>
                  <td className="px-6 py-4">{order.assistedMemberName}</td>
                  <td className="px-6 py-4">{order.orderType}</td>
                  <td className="px-6 py-4">{order.createdAt}</td>
                  <td className="px-6 py-4">{order.status}</td>
                  <td className="px-6 py-4">{order.restaurantName || "N/A"}</td>
                  <td className="px-6 py-4">{order.restaurantAddress || "N/A"}</td>
                  <td className="px-6 py-4">{order.riderName || "–"}</td>
                  <td className="px-6 py-4">{order.riderEmail || "–"}</td>
                  <td className="px-6 py-4 space-y-2">
                    {!order.riderName && (
                      <>
                        <select
                          className="w-full border px-2 py-1 rounded text-sm"
                          value={selectedRiders[order.orderId] || ""}
                          onChange={e => setSelectedRiders(prev => ({ ...prev, [order.orderId]: e.target.value }))}
                        >
                          <option value="">Select Rider</option>
                          {riders.map(r => (
                            <option key={r.id} value={r.id}>
                              {r.name}, {r.email}
                            </option>
                          ))}
                        </select>
                        <button
                          onClick={() => handleAssign(order.orderId, selectedRiders[order.orderId])}
                          disabled={assigning === order.orderId}
                          className="bg-green-600 hover:bg-green-700 text-white px-3 py-1 rounded-xl text-xs w-full disabled:opacity-60"
                        >
                          {assigning === order.orderId ? "Assigning…" : "Assign Rider"}
                        </button>
                      </>
                    )}
                    {order.riderName && <span className="text-green-700 font-semibold">Assigned</span>}
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>

      <div className="flex justify-between items-center mt-6">
        <div className="text-sm text-gray-500">
          Showing {from + 1} to {to} of {flatOrders.length} results
        </div>
        <div className="space-x-2">
          <button onClick={() => setCurrentPage(p => Math.max(p - 1, 1))} disabled={currentPage === 1} className="px-4 py-2 text-sm border rounded-lg disabled:opacity-50">
            Previous
          </button>
          <button onClick={() => setCurrentPage(p => Math.min(p + 1, totalPages))} disabled={currentPage === totalPages} className="px-4 py-2 text-sm border rounded-lg disabled:opacity-50">
            Next
          </button>
        </div>
      </div>
    </div>
  );
}
