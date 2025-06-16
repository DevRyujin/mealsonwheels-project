import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import companyLogo from "../assets/logo.png";

export function Header() {
  const navigate = useNavigate();

  const handleLogout = () => {
    try {
      localStorage.clear(); // clears all user-related storage
      navigate("/login", { replace: true });
    } catch (err) {
      console.error("Logout error:", err);
    }
  };

  const userRole = localStorage.getItem("userType");
  const userName = localStorage.getItem("userName");
  const isLoggedIn = localStorage.getItem("token");

  return (
    <header className="bg-red-700 text-white w-full shadow-md tracking-widest font-medium">
      <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo + Brand */}
          <div className="flex items-center text-2xl font-bold tracking-wide lg:ml-[-10px]">
            <img src={companyLogo} alt="Logo" className="h-24" />
            Meals&nbsp;on&nbsp;<span className="text-black">Wheels</span>
          </div>

          {/* Static Public Navigation */}
          <nav className="flex-1 flex justify-center lg:ml-[-5px]">
            <ul className="flex space-x-8">
              <li>
                <NavLink
                  to="/"
                  className={({ isActive }) =>
                    `text-black hover:text-white ${isActive ? "underline font-bold" : ""}`
                  }
                >
                  Home
                </NavLink>
              </li>
              <li>
                <NavLink
                  to="/menu"
                  className={({ isActive }) =>
                    `text-white hover:text-black ${isActive ? "underline font-bold" : ""}`
                  }
                >
                  Menu
                </NavLink>
              </li>
              <li>
                <NavLink
                  to="/about"
                  className={({ isActive }) =>
                    `text-white hover:text-black ${isActive ? "underline font-bold" : ""}`
                  }
                >
                  About
                </NavLink>
              </li>
              <li>
                <NavLink
                  to="/contact"
                  className={({ isActive }) =>
                    `text-white hover:text-black ${isActive ? "underline font-bold" : ""}`
                  }
                >
                  Contact
                </NavLink>
              </li>
            </ul>
          </nav>

          {/* User Info + Auth Buttons */}
          <div className="flex items-center space-x-4 lg:mr-4">
            {isLoggedIn ? (
              <>
                <span className="font-semibold">{userName}</span>
                <span className="italic text-gray-300">{userRole}</span>
                <button
                  onClick={handleLogout}
                  className="bg-red-700 text-white border border-white px-6 py-2 rounded-lg hover:bg-white hover:text-red-700 transition"
                >
                  Logout
                </button>
              </>
            ) : (
              <>
                <NavLink
                  to="/login"
                  className="bg-red-700 text-white border border-white px-6 py-2 rounded-lg hover:bg-white hover:text-red-700 transition"
                >
                  Login
                </NavLink>
                <NavLink
                  to="/register"
                  className="text-white px-4 py-2 hover:underline"
                >
                  Register
                </NavLink>
              </>
            )}
          </div>
        </div>

        {/* Dev-Only Buttons for Testing Roles */}
        {process.env.NODE_ENV === "development" && (
          <div className="flex justify-center mt-2 space-x-2">
            {["admin", "member", "partner", "volunteer"].map((role) => (
              <button
                key={role}
                onClick={() => {
                  localStorage.setItem("userType", role);
                  localStorage.setItem("userName", `Test${role}`);
                  localStorage.setItem("token", "dummyToken");
                  window.location.href = `/${role}/dashboard`;
                }}
                className="bg-yellow-500 text-black font-bold px-4 py-1 rounded hover:bg-yellow-400 transition text-sm"
              >
                Set {role}
              </button>
            ))}
          </div>
        )}
      </div>
    </header>
  );
}
