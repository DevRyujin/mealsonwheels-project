import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance";
import axios from "axios";

const validateForm = (formData, role) => {
  const requiredFields = ["name", "email", "password", "phone", "address"];
  for (const field of requiredFields) {
    if (!formData[field]) return `Please fill in your ${field}.`;
  }

  if (!role) return "Please select a role.";

  if (role === "CAREGIVER") {
    if (!formData.memberName || !formData.memberContact || !formData.memberAddress || !formData.relationToMember) {
      return "Please complete all caregiver-related fields.";
    }
  } else if (role === "PARTNER") {
    if (!formData.restaurantName || !formData.partnershipDuration) {
      return "Please complete all partner-related fields.";
    }
  } else if (role === "RIDER") {
    if (formData.availableDays.length === 0) {
      return "Please select available days.";
    }
  }

  return null;
};

const humanReadableError = (err) => {
  const rawData = err?.response?.data;

  const msg =
    typeof rawData === "string"
      ? rawData
      : rawData?.message || err.message || "Registration failed. Please try again.";

  const lower = msg.toLowerCase();

  if (lower.includes("email")) {
    return { message: "This email is already registered.", field: "email" };
  }

  if (lower.includes("phone")) {
    return { message: "Phone number is already in use.", field: "phone" };
  }

  if (
    lower.includes("validation") ||
    lower.includes("bad request") ||
    lower.includes("invalid")
  ) {
    return {
      message: "Please fill all required fields correctly.",
      field: "",
    };
  }

  return {
    message: msg,
    field: "",
  };
};


const inputClass = "w-full p-3 shadow rounded-xl border border-gray-300 focus:outline-none focus:border-blue-600 border-2 transition-all";

const RegisterForm = () => {
  const navigate = useNavigate();
  const [role, setRole] = useState("");
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    phone: "",
    address: "",
    latitude: "",
    longitude: "",
    dietaryRestrictions: [],
    memberName: "",
    memberContact: "",
    memberAddress: "",
    relationToMember: "",
    restaurantName: "",
    partnershipDuration: "",
    availableDays: []
  });

  const [error, setError] = useState("");
  const [highlightedErrorField, setHighlightedErrorField] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleCheckboxChange = (e) => {
    const { name, value, checked } = e.target;
    const val = value.toUpperCase();

    setFormData((prev) => {
      const updated = new Set(prev[name]);
      if (checked) updated.add(val);
      else updated.delete(val);
      return { ...prev, [name]: Array.from(updated) };
    });
  };

  const handleDietaryChange = (e) => {
    const { value, checked } = e.target;
    let updated = [...formData.dietaryRestrictions];

    if (value === "None") {
      updated = checked ? ["None"] : [];
    } else {
      updated = updated.filter((v) => v !== "None");
      if (checked) updated.push(value);
      else updated = updated.filter((v) => v !== value);
    }

    setFormData((prev) => ({ ...prev, dietaryRestrictions: updated }));
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

          setFormData((prev) => ({
            ...prev,
            latitude,
            longitude,
            address
          }));

          alert("Location fetched successfully!");
        } catch (error) {
          alert("Failed to fetch address from coordinates.");
          console.error("Reverse geocoding error:", error);
        }
      },
      (error) => {
        alert("Failed to get location: " + error.message);
      }
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError("");
    setHighlightedErrorField("");

    const validationError = validateForm(formData, role);
    if (validationError) {
      setError(validationError);
      setLoading(false);
      return;
    }

    try {
      const payload = {
        name: formData.name,
        email: formData.email,
        password: formData.password,
        phone: formData.phone,
        address: formData.address,
        latitude: parseFloat(formData.latitude),
        longitude: parseFloat(formData.longitude),
        role
      };

      if (role === "MEMBER") {
        payload.memberProfileDTO = {
          dietaryRestrictions: formData.dietaryRestrictions,
          address: formData.address,
          memberLocationLat: parseFloat(formData.latitude),
          memberLocationLong: parseFloat(formData.longitude),
          approved: false
        };
      } else if (role === "CAREGIVER") {
        payload.caregiverProfileDTO = {
          memberNameToAssist: formData.memberName,
          memberPhoneNumberToAssist: formData.memberContact,
          memberAddressToAssist: formData.memberAddress,
          memberRelationship: formData.relationToMember
        };
      } else if (role === "PARTNER") {
        payload.partnerProfileDTO = {
          companyName: formData.restaurantName,
          partnershipDuration: formData.partnershipDuration
        };
      } else if (role === "RIDER") {
        payload.riderProfileDTO = {
          availableDays: formData.availableDays
        };
      }

      await axiosInstance.post("/auth/register", payload);
      navigate("/register/success");
    } catch (err) {
      const { message, field } = humanReadableError(err);
      setError(message);
      setHighlightedErrorField(field);

      if (field) {
        const input = document.querySelector(`input[name="${field}"]`);
        if (input) {
          input.scrollIntoView({ behavior: "smooth", block: "center" });
          input.focus();
        }
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-lg mx-auto mt-10 p-12 mb-24 rounded-xl shadow-md bg-gradient-to-r from-orange-100 to-red-400">
      <h2 className="text-3xl font-bold text-center text-indigo-900 mb-6">Create Account</h2>
      {error && <p className="text-red-500 text-center mb-4">{error}</p>}

      <form onSubmit={handleSubmit} className="space-y-4">
        <input name="name" value={formData.name} onChange={handleChange} placeholder="Full Name" className={inputClass} />
        
        <input
          name="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="Email"
          className={`${inputClass} ${highlightedErrorField === "email" ? "border-red-500" : ""}`}
        />

        <input name="password" value={formData.password} onChange={handleChange} placeholder="Password" type="password" className={inputClass} />

        <input
          name="phone"
          value={formData.phone}
          onChange={handleChange}
          placeholder="Phone"
          className={`${inputClass} ${highlightedErrorField === "phone" ? "border-red-500" : ""}`}
        />

        <div className="flex flex-col sm:flex-row gap-2">
          <input name="address" value={formData.address} onChange={handleChange} placeholder="Address" className={inputClass} />
          <button type="button" onClick={getLocation} className="flex items-center justify-center bg-gradient-to-r from-orange-300 to-red-600 text-white px-4 py-3 rounded-xl shadow hover:brightness-110 transform transition-transform active:scale-95 whitespace-nowrap">📌 Get Location</button>
        </div>

        <select name="role" value={role} onChange={(e) => setRole(e.target.value)} className={inputClass}>
          <option value="">Select Role</option>
          <option value="MEMBER">Member</option>
          <option value="CAREGIVER">Caregiver</option>
          <option value="PARTNER">Partner</option>
          <option value="RIDER">Rider</option>
        </select>

        {role === "MEMBER" && (
          <div className="space-y-2">
            <label className="block text-sm font-medium text-gray-700">Dietary Restrictions</label>
            <div className="flex flex-wrap gap-4">
              {["Vegan", "Gluten-Free", "Nut-Free", "Low Sodium", "None"].map((option) => (
                <label key={option} className="inline-flex items-center gap-2 text-sm text-gray-800">
                  <input
                    type="checkbox"
                    name="dietaryRestrictions"
                    value={option}
                    checked={formData.dietaryRestrictions.includes(option)}
                    onChange={handleDietaryChange}
                    className="accent-blue-600"
                  />
                  {option}
                </label>
              ))}
            </div>
          </div>
        )}

        {role === "CAREGIVER" && (
          <>
            <input type="text" name="memberName" placeholder="Member's Name" value={formData.memberName} onChange={handleChange} className={inputClass} />
            <input type="text" name="memberContact" placeholder="Member's Contact" value={formData.memberContact} onChange={handleChange} className={inputClass} />
            <input type="text" name="memberAddress" placeholder="Member's Address" value={formData.memberAddress} onChange={handleChange} className={inputClass} />
            <input type="text" name="relationToMember" placeholder="Relation to Member" value={formData.relationToMember} onChange={handleChange} className={inputClass} />
          </>
        )}

        {role === "PARTNER" && (
          <div className="space-y-4">
            <div>
              <label htmlFor="restaurantName" className="block text-sm font-medium text-gray-700">Restaurant/Company Name</label>
              <input name="restaurantName" value={formData.restaurantName} onChange={handleChange} placeholder="Enter your company name" className={inputClass} />
            </div>
            <div>
              <label htmlFor="partnershipDuration" className="block text-sm font-medium text-gray-700">Partnership Duration</label>
              <input name="partnershipDuration" value={formData.partnershipDuration} onChange={handleChange} type="date" className={inputClass} />
            </div>
          </div>
        )}

        {role === "RIDER" && (
          <div className="space-y-4">
            <label className="block text-sm font-medium text-gray-700">Available Days</label>
            <div className="flex flex-wrap gap-4 mt-1">
              {["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"].map((day) => (
                <label key={day} className="inline-flex items-center gap-2 text-sm text-gray-800">
                  <input
                    type="checkbox"
                    name="availableDays"
                    value={day}
                    checked={formData.availableDays.includes(day)}
                    onChange={handleCheckboxChange}
                    className="accent-blue-600"
                  />
                  {day.charAt(0) + day.slice(1).toLowerCase()}
                </label>
              ))}
            </div>
          </div>
        )}

        <div className="flex justify-end gap-4 mt-6">
          <button type="button" onClick={() => navigate("/")} className="px-6 py-2 rounded-xl border border-gray-400 text-gray-700 hover:bg-gray-100 transform transition-transform active:scale-95">
            Cancel
          </button>
          <button type="submit" disabled={loading} className="px-6 py-2 rounded-xl bg-gradient-to-r from-orange-300 to-red-600 text-white hover:brightness-110 transform transition-transform active:scale-95">
            {loading ? "Registering..." : "Register"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default RegisterForm;
