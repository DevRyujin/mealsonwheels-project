import React, { useEffect, useState } from "react";
import { saveAs } from "file-saver";
import axiosInstance from "../../api/axiosInstance"; // make sure this path is correct

export default function ManageDeliveries() {
    const [members, setMembers] = useState([]);
    const [loading, setLoading] = useState(true);

    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 10;

    // === SECURED BACKEND FETCH WITH AXIOS INSTANCE ===
    useEffect(() => {
        const fetchMembers = async () => {
            setLoading(true);
            try {
                const res = await axiosInstance.get("/members"); // your secured endpoint
                setMembers(res.data);
            } catch (err) {
                console.error("Error fetching members:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchMembers();
    }, []);

    // Pagination logic
    const totalItems = members.length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, totalItems);
    const currentItems = members.slice(startIndex, endIndex);

    const handleNext = () => {
        if (currentPage < totalPages) setCurrentPage(prev => prev + 1);
    };

    const handlePrevious = () => {
        if (currentPage > 1) setCurrentPage(prev => prev - 1);
    };

    // === Export to CSV ===
    const exportToCSV = () => {
        if (!members || members.length === 0) return;

        const headers = Object.keys(members[0]).join(",") + "\n";
        const rows = members
            .map(row =>
                Object.values(row)
                    .map(value => `"${String(value).replace(/"/g, '""')}"`)
                    .join(",")
            )
            .join("\n");

        const csvContent = headers + rows;
        const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
        saveAs(blob, "deliveries.csv");
    };

    return (
        <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8 min-h-screen">
            {/* Header */}
            <div className="text-center mt-6 mb-6 mx-2 p-4">
                <h1 className="text-3xl font-extrabold text-gray-900">Deliveries Dashboard</h1>
            </div>

            {/* Table Header */}
            <div className="flex justify-between bg-gray-300 p-5 mx-auto rounded-xl">
                <h2 className="text-2xl font-medium text-gray-900">Delivery Status Overview</h2>
                <button
                    onClick={exportToCSV}
                    className="text-md font-medium text-white bg-indigo-900 rounded-2xl p-2 px-4 hover:bg-indigo-800"
                >
                    Export Data
                </button>
            </div>

            {/* Table */}
            <div className="overflow-x-auto mt-4">
                <table className="min-w-full text-sm text-left text-gray-700">
                    <thead className="bg-gray-100 text-xs uppercase text-gray-800">
                        <tr>
                            <th className="px-6 py-3">NO.</th>
                            <th className="px-6 py-3">MEMBER</th>
                            <th className="px-6 py-3">MEAL</th>
                            <th className="px-6 py-3">ORDER DATE & TIME</th>
                            <th className="px-6 py-3">COOKING START</th>
                            <th className="px-6 py-3">STATUS</th>
                            <th className="px-6 py-3">RECEIVED</th>
                        </tr>
                    </thead>
                    <tbody>
                        {loading ? (
                            <tr>
                                <td colSpan="7" className="text-center py-6">Loading...</td>
                            </tr>
                        ) : members.length === 0 ? (
                            <tr>
                                <td colSpan="7" className="text-center py-6">No data available</td>
                            </tr>
                        ) : (
                            currentItems.map((member, index) => (
                                <tr key={member.id || index} className="hover:bg-gray-50">
                                    <td className="px-6 py-4">{startIndex + index + 1}</td>
                                    <td className="px-6 py-4">{member.name}</td>
                                    <td className="px-6 py-4">{member.meal}</td>
                                    <td className="px-6 py-4">{member.ordertime}</td>
                                    <td className="px-6 py-4">{member.cookingstart}</td>
                                    <td className="px-6 py-4">{member.status}</td>
                                    <td className="px-6 py-4">{member.receive}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination Controls */}
            <div className="flex justify-end p-4 mt-4 space-x-4">
                <button
                    onClick={handlePrevious}
                    disabled={currentPage === 1}
                    className={`bg-white border rounded-xl px-5 py-2 font-medium ${
                        currentPage === 1
                            ? "border-gray-300 text-gray-400 cursor-not-allowed"
                            : "border-gray-900 text-gray-900 hover:bg-gray-200"
                    }`}
                >
                    Previous
                </button>
                <button
                    onClick={handleNext}
                    disabled={currentPage === totalPages}
                    className={`bg-white border rounded-xl px-9 py-2 font-medium ${
                        currentPage === totalPages
                            ? "border-gray-300 text-gray-400 cursor-not-allowed"
                            : "border-gray-900 text-gray-900 hover:bg-gray-200"
                    }`}
                >
                    Next
                </button>
            </div>

            {/* Footer Info */}
            <div className="text-left text-sm font-medium text-blue-600 px-4 mb-8">
                <p>Showing {startIndex + 1} to {endIndex} out of {totalItems} results</p>
            </div>
        </div>
    );
}
