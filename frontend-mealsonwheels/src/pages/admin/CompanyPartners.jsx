import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance"; // Adjust path if necessary

export default function CompanyPartners() {
    const [partners, setPartners] = useState([]);
    const [loading, setLoading] = useState(true);

    // === SECURED BACKEND FETCH WITH AXIOS INSTANCE ===
    useEffect(() => {
        const fetchPartners = async () => {
            setLoading(true);
            try {
                const res = await axiosInstance.get("/admin/approved-partners");
                setPartners(res.data);
            } catch (err) {
                console.error("Error fetching partners:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchPartners();
    }, []);

    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 min-h-screen">
            {/* Header */}
            <div className="text-center mt-6 mb-6 mx-2 p-4">
                <h1 className="text-3xl font-extrabold text-gray-900">Our Partners</h1>
                <p className="text-gray-700 mt-2 font-medium">Organizations collaborating with MerryMeals charity</p>
            </div>

            {/* Card Section */}
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 justify-items-center">
                {loading ? (
                    <p className="text-center col-span-full text-gray-400">Loading partners...</p>
                ) : partners.length === 0 ? (
                    <p className="text-center col-span-full text-gray-400">No partners found.</p>
                ) : (
                    partners.map((partner) => (
                        <div
                            key={partner.id}
                            className="bg-white shadow-md rounded-xl p-8 m-3 w-full sm:w-[260px] md:w-[280px] border border-gray-200 hover:shadow-lg transition-shadow"
                        >
                            <h2 className="text-center text-xl font-bold mb-2 text-gray-800">
                                {partner.companyName}
                            </h2>
                            <div className="space-y-2 text-sm text-gray-700">
                                <p><span className="font-semibold">Address:</span> {partner.address}</p>
                                <p><span className="font-semibold">Email:</span> {partner.email}</p>
                                <p><span className="font-semibold">Partnership Duration:</span> {partner.partnershipDuration}</p>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
