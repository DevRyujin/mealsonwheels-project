import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUtensils, FaClipboardList, FaStar } from 'react-icons/fa';
import { mealService } from '../../services/mealService';

export default function PartnerDashboard() {
  const navigate = useNavigate();
  const [meals, setMeals] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [editForm, setEditForm] = useState({});

  useEffect(() => {
    fetchMeals();
  }, []);

  const fetchMeals = async () => {
    try {
      const data = await mealService.getMeals();
      console.log('✅ Fetched meals:', data); 
      setMeals(data);
    } catch (error) {
      console.error('Failed to fetch meals:', error);
    }
  };

  const handleDelete = async (mealId) => {
    try {
      await mealService.deleteMeal(mealId);
      alert("Meal deleted successfully!");
      fetchMeals(); // Refresh the list
    } catch (error) {
      console.error("Failed to delete meal:", error);

      const errorMessage =
        error?.response?.data?.message || "Meal cannot be deleted. It may have already been ordered.";

      alert(errorMessage);
    }
  };


  const handleEditClick = (meal) => {
    setEditingId(meal.id);
    setEditForm({ mealName: meal.mealName, mealDesc: meal.mealDesc });
  };

  const handleConfirmUpdate = async (id) => {
    try {
      await mealService.updateMeal(id, { ...editForm, id });
      setEditingId(null);
      fetchMeals();
    } catch (error) {
      console.error('Failed to update meal:', error);
    }
  };

  return (
    <div className="bg-gray-50 min-h-screen px-4 py-8 md:px-8">
      <div className="max-w-7xl mx-auto">
        {/* Welcome Header */}
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-gray-800 mb-1">Welcome, Syndicate Restaurant!</h1>
          <p className="text-gray-500">Manage your menus and delight your customers.</p>
        </div>

        {/* Dashboard Summary Cards */}
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4 mb-8">
          <SummaryCard icon={<FaUtensils />} title="Total Meals" value={meals.length} />
          <SummaryCard icon={<FaClipboardList />} title="Active Orders" value="0" />
          <SummaryCard icon={<FaStar />} title="Customer Rating" value="0/5" />
        </div>

        {/* Menu Section Header */}
        <div className="flex flex-col md:flex-row md:items-center md:justify-between mb-4 gap-2">
          <h2 className="text-xl font-bold text-gray-800">Your Meals</h2>
          <div className="flex gap-2">
            <button
              className="px-5 py-2 bg-gray-800 text-white rounded hover:bg-gray-700 transition"
              onClick={() => navigate('/partner/add-menu')}
            >
              + Add Menu
            </button>
            <button
              className="px-5 py-2 border border-gray-300 text-gray-800 rounded hover:bg-gray-200 transition"
              onClick={() => navigate('/partner/partnersfoodsafety')}
            >
              Food Safety
            </button>
          </div>
        </div>

        {/* Meal Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {meals.map((meal) => (
            <div key={meal.id} className="bg-white shadow rounded p-4 relative">
              {editingId === meal.id ? (
                <>
                  <input
                    type="text"
                    value={editForm.mealName}
                    onChange={(e) =>
                      setEditForm((prev) => ({ ...prev, mealName: e.target.value }))
                    }
                    className="w-full border rounded p-2 mb-2"
                  />
                  <textarea
                    value={editForm.mealDesc}
                    onChange={(e) =>
                      setEditForm((prev) => ({ ...prev, mealDesc: e.target.value }))
                    }
                    className="w-full border rounded p-2 mb-2"
                  />
                  <div className="flex gap-2">
                    <button
                      className="px-4 py-1 bg-green-600 text-white rounded"
                      onClick={() => handleConfirmUpdate(meal.id)}
                    >
                      Confirm
                    </button>
                    <button
                      className="px-4 py-1 bg-gray-400 text-white rounded"
                      onClick={() => setEditingId(null)}
                    >
                      Cancel
                    </button>
                  </div>
                </>
              ) : (
                <>
                  <div className="relative">
                    {meal.photoData && (
                      <img
                        src={`data:${meal.mealPhotoType};base64,${meal.photoData}`}
                        alt={meal.mealName}
                        className="h-40 w-full object-cover rounded mb-2"
                      />
                    )}

                    {meal.mealType && (
                      <div
                        className={`absolute top-2 right-2 px-3 py-1 text-xs font-semibold text-white rounded-full z-10 ${
                          meal.mealType === 'HOT'
                            ? 'bg-red-500'
                            : meal.mealType === 'FROZEN'
                            ? 'bg-blue-500'
                            : 'bg-gray-400'
                        }`}
                      >
                        {meal.mealType}
                      </div>
                    )}
                  </div>

                  <h3 className="text-lg font-semibold text-gray-800">{meal.mealName}</h3>
                  <p className="text-sm text-gray-600 mb-2">{meal.mealDesc}</p>
                  <div className="flex gap-2">
                    <button
                      className="px-4 py-1 bg-blue-600 text-white rounded"
                      onClick={() => handleEditClick(meal)}
                    >
                      Update
                    </button>
                    <button
                      className="px-4 py-1 bg-red-600 text-white rounded"
                      onClick={() => handleDelete(meal.id)}
                    >
                      Delete
                    </button>
                  </div>
                </>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

// Summary Card Reusable
function SummaryCard({ icon, title, value }) {
  return (
    <div className="bg-white shadow rounded p-5 text-center hover:shadow-lg transition-shadow duration-300">
      <div className="mb-2">{icon}</div>
      <div className="text-sm text-gray-600">{title}</div>
      <div className="text-2xl font-semibold text-gray-800">{value}</div>
    </div>
  );
}
