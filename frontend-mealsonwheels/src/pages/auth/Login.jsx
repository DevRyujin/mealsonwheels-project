import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance";

const LoginForm = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    if (!formData.email || !formData.password) {
      setError("Please enter both email and password.");
      return;
    }

    try {
  setLoading(true);
  const response = await axiosInstance.post("/auth/login", formData);

  console.log("✅ Login response data:", response.data); // Debug

  const { token, userType, name, user } = response.data;

  localStorage.setItem("token", token);
  localStorage.setItem("userType", userType.toLowerCase());
  localStorage.setItem("userName", name);

  // ✅ Safely store user object
  if (user) {
    localStorage.setItem("user", JSON.stringify(user));
  } else {
    console.warn("⚠️ No user object found in response.");
  }

  switch (userType) {
    case "ROLE_ADMIN":
      navigate("/admin/dashboard");
      break;
    case "ROLE_PARTNER":
      navigate("/partner");
      break;
    case "ROLE_VOLUNTEER":
      navigate("/volunteer");
      break;
    case "ROLE_CAREGIVER":
      navigate("/caregiver");
      break;
    case "ROLE_MEMBER":
      navigate("/member");
      break;
    default:
      navigate("/");
  }
} catch (err) {
  const status = err?.response?.status;
  const msg = err?.response?.data?.message?.toLowerCase() || "";

  if (status === 403 && msg.includes("not approved")) {
    navigate("/approval-pending");
  } else if (msg.includes("credentials")) {
    setError("Invalid email or password.");
  } else {
    console.error("❌ Login error:", err); // <--- Add this
    setError("Login failed. Please try again.");
  }
} finally {
  setLoading(false);
}

  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-2">
      <div className="w-full max-w-sm">
        <div className="bg-gradient-to-r from-orange-100 to-red-400 rounded-lg shadow-md p-12">
          <div className="mb-6">
            <h2 className="text-lg font-bold">Login to your account</h2>
            <p className="text-sm text-gray-600">Enter your email below to login to your account</p>
            <div className="mt-2">
              <button className="text-sm font-semibold text-indigo-900 hover:underline" onClick={() => navigate("/register")}>Sign Up</button>
            </div>
          </div>

          {error && <p className="text-red-500 text-sm mb-4">{error}</p>}

          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid gap-2">
              <label htmlFor="email" className="text-sm font-medium">Email</label>
              <input
                type="email"
                id="email"
                name="email"
                placeholder="m@example.com"
                value={formData.email}
                onChange={handleChange}
                required
                className="w-full px-4 py-2 border border-gray-300 rounded"
              />
            </div>
            <div className="grid gap-2">
              <div className="flex items-center justify-between">
                <label htmlFor="password" className="text-sm font-medium">Password</label>
                <a href="#" className="text-sm font-semibold text-indigo-900 hover:underline">Forgot your password?</a>
              </div>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                required
                className="w-full px-4 py-2 border border-gray-300 rounded"
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full bg-gradient-to-r from-orange-600 to-red-600 text-white py-2 rounded hover:bg-gray-800"
            >
              {loading ? "Logging in..." : "Login"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
