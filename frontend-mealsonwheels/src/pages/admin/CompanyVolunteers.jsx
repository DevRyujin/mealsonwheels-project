import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance"; // Adjust path as needed

export default function CompanyVolunteers() {
    const [volunteers, setVolunteers] = useState([]);
    const [loading, setLoading] = useState(true);

    // === SECURED BACKEND FETCH WITH AXIOS INSTANCE ===
    useEffect(() => {
        const fetchVolunteers = async () => {
            setLoading(true);
            try {
                const res = await axiosInstance.get("/admin/approved-volunteers");
                setVolunteers(res.data);
            } catch (err) {
                console.error("Error fetching volunteers:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchVolunteers();
    }, []);

    const formatServiceType = (type) => {
        switch (type) {
            case "KITCHEN":
                return "Kitchen";
            case "DELIVERY":
                return "Delivery";
            case "PACKAGE":
                return "Package";
            default:
                return type;
        }
    };


    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 min-h-screen">
            {/* Page Header */}
            <div className="text-center mb-10">
                <h1 className="text-3xl font-extrabold text-gray-900">Company Volunteers</h1>
                <p className="text-gray-700 mt-2 font-medium">
                    All company volunteers registered with MerryMeals
                </p>
            </div>

            {/* Volunteer Cards */}
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                {loading ? (
                    <p className="text-center col-span-full text-gray-400">Loading volunteers...</p>
                ) : volunteers.length === 0 ? (
                    <p className="text-center col-span-full text-gray-500">No volunteers found.</p>
                ) : (
                    volunteers.map((volunteer) => (
                        <div
                            key={volunteer.id}
                            className="bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition duration-300 p-5 flex flex-col justify-between"
                        >
                            <h2 className="text-lg font-semibold text-indigo-800 text-center mb-2">
                                {volunteer.name}
                            </h2>
                            <h3 className="text-center text-sm text-gray-600 mb-3 italic">
                                <p><span>Exp: {volunteer.volunteerDuration}</span></p> 
                            </h3>
                            <div className="text-sm text-gray-700 space-y-1">
                                <p><span className="font-semibold">Address:</span> {volunteer.address}</p>
                                <p><span className="font-semibold">Email:</span> {volunteer.email}</p>
                                <p><span className="font-semibold">Phone:</span> {volunteer.phone}</p>
                                <p>
                                    <span className="font-semibold">Service:</span>{" "}
                                    {formatServiceType(volunteer.serviceType)}
                                </p>

                                <p>
                                <span className="font-semibold">Availability:</span>{" "}
                                {Array.isArray(volunteer.availableDays)
                                    ? volunteer.availableDays.join(", ")
                                    : volunteer.availableDays}
                                </p>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
