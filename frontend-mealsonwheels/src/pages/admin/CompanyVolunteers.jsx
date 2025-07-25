import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance"; // Adjust if needed

export default function CompanyRiders() {
    const [riders, setRiders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchRiders = async () => {
            setLoading(true);
            try {
                const res = await axiosInstance.get("/admin/approved-riders");
                setRiders(res.data);
            } catch (err) {
                console.error("Error fetching riders:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchRiders();
    }, []);

    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 min-h-screen">
            <div className="text-center mb-10">
                <h1 className="text-3xl font-extrabold text-gray-900">Company Riders</h1>
                <p className="text-gray-700 mt-2 font-medium">
                    All approved riders registered with MerryMeals
                </p>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                {loading ? (
                    <p className="text-center col-span-full text-gray-400">Loading riders...</p>
                ) : riders.length === 0 ? (
                    <p className="text-center col-span-full text-gray-500">No riders found.</p>
                ) : (
                    riders.map((rider) => (
                        <div
                            key={rider.id}
                            className="bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition duration-300 p-5 flex flex-col justify-between"
                        >
                            <h2 className="text-lg font-semibold text-indigo-800 text-center mb-2">
                                {rider.name}
                            </h2>
                            <div className="text-sm text-gray-700 space-y-1">
                                <p><span className="font-semibold">Address:</span> {rider.address}</p>
                                <p><span className="font-semibold">Email:</span> {rider.email}</p>
                                <p><span className="font-semibold">Phone:</span> {rider.phone}</p>
                                <p><span className="font-semibold">Driver's License:</span> {rider.driverLicenseNumber}</p>
                                <p><span className="font-semibold">License Expiry:</span> {rider.licenseExpiryDate?.split("T")[0]}</p>
                                <p><span className="font-semibold">Partner:</span> {rider.partnerCompanyName}</p>
                                <p><span className="font-semibold">Available Days:</span> {rider.availableDays?.join(", ")}</p>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
