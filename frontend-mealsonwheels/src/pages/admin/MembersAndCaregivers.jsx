import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance";

export default function MembersAndCaregivers() {
    const [members, setMembers] = useState([]);
    const [caregivers, setCaregivers] = useState([]);
    const [loading, setLoading] = useState(true);

    // === BACKEND FETCH ===
    useEffect(() => {
        const fetchUsers = async() => {
            setLoading(true);
            try {
                const [membersRes, caregiversRes] = await Promise.all([
                    axiosInstance.get("/members"),
                    axiosInstance.get("/caregivers",)
                ]);

                setMembers(membersRes.data);
                setCaregivers(caregiversRes.data);
            } catch (err) {
                console.error("Error fetching users:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchUsers();
    }, []);
    
    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 min-h-screen">
            {/* Header */}
            <div className="text-center mb-10">
                <h1 className="text-3xl font-extrabold text-gray-900">Members and Caregivers</h1>
                <p className="text-gray-700 mt-2 font-medium">
                All the members and caregivers registered with MerryMeals charity
                </p>
            </div>

            {/* Members Section */}
            <section className="mb-12">
                <h2 className="text-2xl font-bold text-indigo-800 mb-4">Members</h2>
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                {loading ? (
                    <p className="col-span-full text-center text-gray-400">Loading Members...</p>
                ) : members.length === 0 ? (
                    <p className="col-span-full text-center text-gray-500">No members found.</p>
                ) : (
                    members.map((user) => (
                    <div key={user.id} className="bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition duration-300 p-5 flex flex-col justify-between">
                        <h3 className="text-lg font-semibold text-center text-indigo-700 mb-2">{user.name}</h3>
                        <div className="text-sm text-gray-700 space-y-1">
                            <p><strong>Address:</strong> {user.address}</p>
                            <p><strong>Email:</strong> {user.email}</p>
                            <p><strong>Duration:</strong> {user.duration}</p>
                            <p><strong>Extends Reason:</strong> {user.extendsReason}</p>
                        </div>
                    </div>
                    ))
                )}
                </div>
            </section>

            {/* Caregivers Section */}
            <section>
                <h2 className="text-2xl font-bold text-green-800 mb-4">Caregivers</h2>
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                {loading ? (
                    <p className="col-span-full text-center text-gray-400">Loading Caregivers...</p>
                ) : caregivers.length === 0 ? (
                    <p className="col-span-full text-center text-gray-500">No caregivers found.</p>
                ) : (
                    caregivers.map((user) => (
                    <div key={user.id} className="bg-white border border-gray-200 rounded-xl shadow-sm hover:shadow-lg transition duration-300 p-5 flex flex-col justify-between">
                        <h3 className="text-lg font-semibold text-center text-green-700 mb-2">{user.name}</h3>
                        <div className="text-sm text-gray-700 space-y-1">
                            <p><strong>Address:</strong> {user.address}</p>
                            <p><strong>Email:</strong> {user.email}</p>
                            <p><strong>Phone:</strong> {user.phone}</p>
                        </div>
                    </div>
                    ))
                )}
                </div>
            </section>
        </div>

    );
}
