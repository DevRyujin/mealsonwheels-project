import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance"; // Adjust path if needed

export default function MenuDescriptions() {
    const [menus, setMenus] = useState([]);
    const [loading, setLoading] = useState(true);

    // === SECURED BACKEND FETCH WITH AXIOS INSTANCE ===
    useEffect(() => {
        const fetchMenus = async () => {
            setLoading(true);
            try {
                const res = await axiosInstance.get("/menus");
                setMenus(res.data);
            } catch (err) {
                console.error("Error fetching menus:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchMenus();
    }, []);

    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 min-h-screen">
            {/* Header */}
            <div className="text-center mb-10">
                <h1 className="text-3xl font-extrabold text-gray-900">Our Delicious Menus</h1>
            </div>

            {/* Card Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
                {loading ? (
                    <p className="text-center col-span-full text-gray-400">Loading menus...</p>
                ) : menus.length === 0 ? (
                    <p className="text-center col-span-full text-gray-400">No menus found.</p>
                ) : (
                    menus.map((menu) => (
                        <div
                            key={menu.id}
                            className="bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition duration-300 p-5 flex flex-col"
                        >
                            <img
                                src={menu.menuPicture}
                                alt={menu.menuTitle}
                                className="w-full h-48 object-cover rounded-md mb-4"
                            />
                            <h3 className="text-lg font-semibold text-gray-700 mb-1">
                                {menu.menuTitle}
                            </h3>
                            <p className="text-gray-600 mb-4">{menu.menuDescription}</p>

                            <div className="flex justify-end mt-auto space-x-2">
                                <button className="bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600">
                                    Update
                                </button>
                                <button className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600">
                                    Delete
                                </button>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
}
