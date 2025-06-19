import React, { useEffect, useState } from "react";
import axiosInstance from "../../api/axiosInstance"; // make sure this path is correct

export default function AdminInfo() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    age: "",
    contact: "",
    address: ""
  });

  const [isEditing, setIsEditing] = useState(false);
  const [originalData, setOriginalData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [loadError, setLoadError] = useState(false);

  // === BACKEND FETCH WITH AXIOS ===
  useEffect(() => {
    const fetchAdminInfo = async () => {
      try {
        const res = await axiosInstance.get("/admin-info");
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

  if (loading) return <p className="text-center mt-10">Loading admin info...</p>;
  if (loadError) return <p className="text-center mt-10 text-red-600">Failed to load admin info. Please try again later.</p>;
  if (!formData) return <p className="text-center mt-10">No admin data found.</p>;

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

  const handleSave = async () => {
    try {
      const res = await axiosInstance.put("/admin-info", formData);
      setFormData(res.data);
      setOriginalData(res.data);
      setIsEditing(false);
      console.log("Saved successfully:", res.data);
    } catch (err) {
      console.error("Error saving admin info:", err);
      alert("Failed to save changes. Please try again.");
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 min-h-screen">
      {/* Header */}
      <div className="text-center mt-6 mb-6 mx-2 p-4">
        <h1 className="text-3xl font-extrabold text-gray-900">Update Admin Information</h1>
      </div>

      {/* Form */}
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

          {/* Email Address */}
          <div>
            <label className="block text-sm font-medium text-gray-700">Email Address</label>
            <input 
              type="email" 
              name="email" 
              value={formData.email}
              disabled={!isEditing}
              onChange={handleChange}
              className="w-full mt-1 p-2 border rounded-2xl" 
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
                name="contact" 
                value={formData.contact}
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
