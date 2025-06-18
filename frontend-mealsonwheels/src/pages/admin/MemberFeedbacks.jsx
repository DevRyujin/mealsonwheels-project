import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance";

export default function MemberFeedbacks() {
    const [loading, setLoading] = useState(false);
    const [expandedIds, setExpandedIds] = useState([]);
    const [feedbacks, setFeedbacks] = useState([]);

    // === SECURED BACKEND FETCH WITH AXIOS INSTANCE ===
    useEffect(() => {
        const fetchFeedbacks = async () => {
            setLoading(true);
            try {
                const res = await axiosInstance.get("/feedbacks");
                setFeedbacks(res.data);
            } catch (err) {
                console.error("Error fetching feedbacks:", err);
            } finally {
                setLoading(false);
            }
        };
        fetchFeedbacks();
    }, []);

    const toggleExpand = (id) => {
        setExpandedIds((prev) =>
            prev.includes(id)
                ? prev.filter((item) => item !== id)
                : [...prev, id]
        );
    };

    return (
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 min-h-screen">
            <div className="text-center mt-6 mb-6 mx-2 p-4">
                <h1 className="text-2xl font-extrabold text-gray-900">Member Feedbacks</h1>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
                {loading ? (
                    <p className="col-span-full text-center text-gray-400">Loading feedbacks...</p>
                ) : feedbacks.length === 0 ? (
                    <p className="col-span-full text-center text-gray-500">No feedbacks found.</p>
                ) : (
                    feedbacks.map((feedback) => {
                        const isExpanded = expandedIds.includes(feedback.id);
                        return (
                            <div
                                key={feedback.id}
                                className="bg-white border border-gray-200 rounded-xl shadow-md p-6 hover:shadow-lg transition-shadow duration-300 flex flex-col justify-between"
                            >
                                <div className="flex justify-between items-center mb-2">
                                    <h2 className="text-lg font-semibold text-indigo-700">{feedback.userName}</h2>
                                    <p className="text-sm text-gray-500">{feedback.feedbackDate}</p>
                                </div>
                                <div className="text-sm text-gray-700 space-y-1 flex-grow">
                                    <p><strong>Menu:</strong> {feedback.menuName}</p>
                                    <p><strong>Caregiver:</strong> {feedback.caregiverName}</p>
                                    <p><strong>Rating:</strong> {feedback.stars} ⭐</p>
                                    <div>
                                        <p className="font-semibold mt-2">Comment:</p>
                                        <div className={`text-gray-600 ${isExpanded ? "" : "max-h-20 overflow-hidden"} transition-all`}>
                                            {feedback.userComment}
                                        </div>
                                        {feedback.userComment.length > 100 && (
                                            <button
                                                onClick={() => toggleExpand(feedback.id)}
                                                className="text-blue-600 hover:underline text-xs mt-1"
                                            >
                                                {isExpanded ? "Read less" : "Read more"}
                                            </button>
                                        )}
                                    </div>
                                </div>
                            </div>
                        );
                    })
                )}
            </div>
        </div>
    );
}
