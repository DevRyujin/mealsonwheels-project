import { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance";

const roles = ["MEMBER", "VOLUNTEER", "PARTNER", "CAREGIVER", "SUPPORTER", "RIDER", "DONOR"];

export default function Dashboard() {
  const [stats, setStats] = useState({ members: 0, meals: 0, orders: 0, donations: 0 });
  const [loadingStats, setLoadingStats] = useState(true);

  const [users, setUsers] = useState([]);
  const [selectedRole, setSelectedRole] = useState("MEMBER");
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const usersPerPage = 10;

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const res = await axiosInstance.get("/admin/statistics");
        setStats({
        members: res.data.totalMembers,
        meals: res.data.mealsServed,
        orders: res.data.ordersDelivered,
        donations: res.data.totalDonationsReceived,
      });
      } catch (err) {
        console.error("Failed to fetch stats:", err);
      } finally {
        setLoadingStats(false);
      }
    };
    fetchStats();
  }, []);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const res = await axiosInstance.get(`/admin/users?role=${selectedRole}`);
        setUsers(res.data);
        setCurrentPage(1);
      } catch (err) {
        console.error("Failed to fetch users:", err);
      }
    };
    fetchUsers();
  }, [selectedRole]);

  const handleSearch = (e) => {
    setSearchTerm(e.target.value.toLowerCase());
    setCurrentPage(1);
  };

  const handleApprove = async (id) => {
    try {
      await axiosInstance.put(`/admin/approve-user/${id}`);
      setUsers(prev => prev.filter(u => u.id !== id));
    } catch (err) {
      console.error("Error approving user:", err);
    }
  };

  const handleReject = async (id) => {
    try {
      await axiosInstance.put(`/admin/reject-user/${id}`);
      setUsers(prev => prev.filter(u => u.id !== id));
    } catch (err) {
      console.error("Error rejecting user:", err);
    }
  };

  const filteredUsers = users.filter(
    (user) =>
      user.name?.toLowerCase().includes(searchTerm) ||
      user.email?.toLowerCase().includes(searchTerm)
  );

  const totalPages = Math.ceil(filteredUsers.length / usersPerPage);
  const paginatedUsers = filteredUsers.slice(
    (currentPage - 1) * usersPerPage,
    currentPage * usersPerPage
  );

  return (
    <div className="max-w-8xl mx-auto">
      {/* Hero Section */}
      <div className="relative h-[530px] overflow-hidden">
        <img
          src="/images/vegetables.webp"
          alt="vegetables"
          className="absolute w-full bottom-0 h-[530px] object-cover object-bottom"
        />
        <h1 className="absolute z-10 text-white text-3xl sm:text-4xl md:text-5xl lg:text-6xl font-bold top-1/2 transform -translate-y-1/2 text-center px-4 w-full max-w-5xl mx-auto tracking-widest">
          BEHIND EVERY MEAL, A <br className="hidden sm:block" />
          MISSION OF CARE
        </h1>
      </div>


      {/* Stats */}
      <div className="bg-gray-200 mt-6 p-4 rounded-xl text-center shadow-md">
        <h2 className="text-3xl font-semibold text-black">Dashboard Command Center</h2>
        <p className="text-black font-medium">Manage the system in one place</p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 my-10">
        <StatBox label="Members" value={loadingStats ? "..." : stats.members} />
        <StatBox label="Meal Programs" value={loadingStats ? "..." : stats.meals} />
        <StatBox label="Total Orders" value={loadingStats ? "..." : stats.orders} />
        <StatBox label="Total Donations" value={loadingStats ? "..." : stats.donations} />
      </div>

      {/* Approvals */}
      <div className="bg-white shadow rounded-xl p-6 mb-10">
        <h2 className="text-xl font-bold text-gray-800 mb-4">Pending User Approvals</h2>

        {/* Roles and Search */}
        <div className="flex flex-wrap items-center justify-between gap-4 mb-4">
          <div className="flex flex-wrap gap-2">
            {roles.map((role) => (
              <button
                key={role}
                className={`px-4 py-2 rounded-full text-sm font-semibold ${
                  selectedRole === role
                    ? "bg-indigo-900 text-white"
                    : "bg-gray-200 text-gray-800 hover:bg-gray-300"
                }`}
                onClick={() => setSelectedRole(role)}
              >
                {role}
              </button>
            ))}
          </div>
          <input
            type="text"
            placeholder="Search by name or email"
            className="p-2 border border-gray-300 rounded text-sm w-full sm:w-64"
            onChange={handleSearch}
          />
        </div>

        {/* Table */}
        <div className="overflow-x-auto">
          <table className="min-w-full bg-white border border-gray-200 text-sm table-fixed">
            <thead className="bg-gray-100 text-gray-700 uppercase text-xs">
              <tr>
                <th className="py-3 px-4 w-1/4 text-left">Name</th>
                <th className="py-3 px-4 w-1/4 text-left">Email</th>
                <th className="py-3 px-4 w-1/6 text-left">Status</th>
                <th className="py-3 px-4 w-1/4 text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {paginatedUsers.length === 0 ? (
                <tr>
                  <td colSpan="4" className="py-4 px-4 text-center text-gray-500">
                    No users pending approval.
                  </td>
                </tr>
              ) : (
                paginatedUsers.map((user) => (
                  <tr
                    key={user.id}
                    className="border-t hover:bg-gray-50 transition duration-200"
                  >
                    <td className="py-3 px-4">{user.name}</td>
                    <td className="py-3 px-4">{user.email}</td>
                    <td className="py-3 px-4">
                      {user.approved ? "Approved" : "Pending"}
                    </td>
                    <td className="py-3 px-4 text-center space-x-2">
                      <button
                        onClick={() => handleApprove(user.id)}
                        className="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
                      >
                        Approve
                      </button>
                      <button
                        onClick={() => handleReject(user.id)}
                        className="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
                      >
                        Reject
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>

        {/* Pagination */}
        <div className="flex justify-end mt-4 space-x-4">
          <button
            onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
            disabled={currentPage === 1}
            className={`px-4 py-2 border rounded ${
              currentPage === 1 ? "text-gray-400 border-gray-300" : "hover:bg-gray-200"
            }`}
          >
            Previous
          </button>
          <button
            onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))}
            disabled={currentPage === totalPages}
            className={`px-4 py-2 border rounded ${
              currentPage === totalPages ? "text-gray-400 border-gray-300" : "hover:bg-gray-200"
            }`}
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}

function StatBox({ label, value }) {
  return (
    <div className="bg-gray-200 rounded-md p-6 text-center shadow">
      <h3 className="font-semibold text-lg">{label}</h3>
      <p className="text-2xl mt-2 font-bold">{value}</p>
    </div>
  );
}
