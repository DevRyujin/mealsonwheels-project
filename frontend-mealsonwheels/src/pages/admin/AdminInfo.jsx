import React, { useEffect, useState } from "react";
import axios from "axios";
import axiosInstance from "../../api/axiosInstance";

export default function AdminInfo() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    age: "",
    phone: "",
    address: "",
    latitude: "",
    longitude: ""
  });

  const [isEditing, setIsEditing] = useState(false);
  const [originalData, setOriginalData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState(false);

  useEffect(() => {
    const fetchAdminInfo = async () => {
      try {
        const res = await axiosInstance.get("/admin/admin-info"); // ✅ Correct method
        setFormData(res.data);
        setOriginalData(res.data);
      } catch (err) {
        console.error("Failed to fetch admin info:", err);
        setLoadError(true);
      } finally {
        setLoading(false);
      }
    };
    fetchAdminInfo();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleEdit = () => {
    setIsEditing(true);
    setOriginalData(formData);
  };

  const handleCancel = () => {
    setFormData(originalData);
    setIsEditing(false);
  };

  const getLocation = () => {
    if (!navigator.geolocation) {
      alert("Geolocation is not supported by your browser");
      return;
    }

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        const { latitude, longitude } = position.coords;
        try {
          const response = await axios.get(
            `https://nominatim.openstreetmap.org/reverse?format=json&lat=${latitude}&lon=${longitude}`
          );
          const address = response.data.display_name || "";
          setFormData(prev => ({
            ...prev,
            latitude,
            longitude,
            address
          }));
          alert("Location updated!");
        } catch (error) {
          alert("Failed to fetch address from coordinates.");
        }
      },
      (error) => {
        alert("Geolocation error: " + error.message);
      }
    );
  };

  const handleSave = async () => {
    try {
      const { email, ...dataToSend } = formData; // ✅ Remove non-editable field
      const res = await axiosInstance.put("/admin/admin-info", dataToSend);
      setFormData(res.data);
      setOriginalData(res.data);
      setIsEditing(false);
      alert("Admin info updated successfully!");
    } catch (err) {
      console.error("Error saving admin info:", err);
      alert("Failed to save changes.");
    }
  };

  if (loading) return <p className="text-center mt-10">Loading admin info...</p>;
  if (loadError) return <p className="text-center mt-10 text-red-600">Failed to load admin info.</p>;
  if (!formData) return <p className="text-center mt-10">No admin data found.</p>;

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 min-h-screen">
      <div className="text-center mt-6 mb-6 mx-2 p-4">
        <h1 className="text-3xl font-extrabold text-gray-900">Update Admin Information</h1>
      </div>

      <div className="bg-gray-100 p-8 rounded-xl shadow-md">
        <form onSubmit={(e) => e.preventDefault()} className="space-y-4">
          {/* Name */}
          <div>
            <label className="block text-sm font-medium text-gray-700">Name</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              disabled={!isEditing}
              onChange={handleChange}
              className="w-full mt-1 p-2 border rounded-xl"
            />
          </div>

          {/* Email (non-editable) */}
          <div>
            <label className="block text-sm font-medium text-gray-700">Email Address</label>
            <input 
              type="email" 
              name="email" 
              value={formData.email}
              disabled
              className="w-full mt-1 p-2 border rounded-2xl bg-gray-100 cursor-not-allowed" 
            />
          </div>

          {/* Age and Contact */}
          <div className="flex space-x-4">
            <div className="w-1/2">
              <label className="block text-sm font-medium text-gray-700">Age</label>
              <input
                type="number"
                name="age"
                value={formData.age}
                disabled={!isEditing}
                onChange={handleChange}
                className="w-full mt-1 p-2 border rounded-xl"
              />
            </div>

            <div className="w-1/2">
              <label className="block text-sm font-medium text-gray-700">Contact</label>
              <input
                type="text"
                name="phone"
                value={formData.phone}
                disabled={!isEditing}
                onChange={handleChange}
                className="w-full mt-1 p-2 border rounded-xl"
              />
            </div>
          </div>

          {/* Address */}
          <div>
            <label className="block text-sm font-medium text-gray-700">Address</label>
            <textarea
              name="address"
              value={formData.address}
              disabled={!isEditing}
              onChange={handleChange}
              className="w-full mt-1 p-2 border rounded-xl"
              rows="3"
            ></textarea>
          </div>

          {/* Geolocation */}
          {isEditing && (
            <div className="flex gap-4 items-center">
              <div className="flex-1">
                <label className="block text-sm font-medium text-gray-700">Latitude</label>
                <input
                  type="text"
                  name="latitude"
                  value={formData.latitude}
                  onChange={handleChange}
                  className="w-full mt-1 p-2 border rounded-xl"
                />
              </div>
              <div className="flex-1">
                <label className="block text-sm font-medium text-gray-700">Longitude</label>
                <input
                  type="text"
                  name="longitude"
                  value={formData.longitude}
                  onChange={handleChange}
                  className="w-full mt-1 p-2 border rounded-xl"
                />
              </div>
              <button
                type="button"
                onClick={getLocation}
                className="mt-6 px-4 py-2 bg-orange-500 text-white rounded-xl shadow hover:brightness-110"
              >
                📌 Get Location
              </button>
            </div>
          )}

          {/* Buttons */}
          <div className="flex justify-end space-x-4 mt-6">
            {isEditing ? (
              <>
                <button
                  type="button"
                  onClick={handleCancel}
                  className="px-4 py-2 rounded-xl border border-gray-400 text-gray-700 hover:bg-gray-200"
                >
                  Cancel
                </button>
                <button
                  type="button"
                  onClick={handleSave}
                  className="px-6 py-2 rounded-xl bg-indigo-900 text-white hover:bg-indigo-800"
                >
                  Save
                </button>
              </>
            ) : (
              <button
                type="button"
                onClick={handleEdit}
                className="px-4 py-2 rounded-xl bg-indigo-900 text-white hover:bg-indigo-800"
              >
                Update
              </button>
            )}
          </div>
        </form>
      </div>
    </div>
  );
}
