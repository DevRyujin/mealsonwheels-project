import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance";

const inputClass = "w-full p-3 shadow rounded-xl border border-gray-300 focus:outline-none focus:border-blue-600 border-2 transition-all";

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
      const { token, userType, name } = response.data; // ✅ destructure name

      // ✅ Store everything in localStorage
      localStorage.setItem("token", token);
      localStorage.setItem("userType", userType.toLowerCase());
      localStorage.setItem("userName", name); // ✅ now userName will show in HeaderNavbar

      // ✅ Redirect based on role
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
        setError("Login failed. Please try again.");
      }
    } finally {
      setLoading(false);
    }
  };



  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="max-w-md w-full p-8 mb-24 border border-gray-300 rounded-xl shadow-md bg-white">
        <h2 className="text-3xl font-bold text-center text-blue-800 mb-6">Login</h2>
        {error && <p className="text-red-500 text-center mb-4">{error}</p>}

        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="email"
            name="email"
            placeholder="Email Address"
            value={formData.email}
            onChange={handleChange}
            className={inputClass}
          />
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            className={inputClass}
          />

          <div className="flex justify-end gap-4 mt-6">
            <button
              type="button"
              onClick={() => navigate("/")}
              className="px-6 py-2 rounded-xl border border-gray-400 text-gray-700 hover:bg-gray-100 transform transition-transform active:scale-95"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading}
              className="px-6 py-2 rounded-xl bg-gradient-to-r from-orange-500 to-red-500 text-white hover:brightness-110 transform transition-transform active:scale-95"
            >
              {loading ? "Logging in..." : "Login"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
