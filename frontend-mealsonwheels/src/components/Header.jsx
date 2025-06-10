import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import companyLogo from "../assets/logo.png";

export function Header() {

  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      localStorage.removeItem("token");
      localStorage.removeItem("userType");
      localStorage.removeItem("userName");

      navigate("/login");
      
    } catch (err) {
      console.error("Logout error:", err);
    }
  };


  const isLoggedIn = localStorage.getItem("token"); // replace with auth logic

  return (

    
    <header className="bg-red-700 text-white w-full shadow-md tracking-widest font-medium">
      <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          
          <div className="flex items-center text-2xl font-bold tracking-wide lg:ml-[-10px]">
            <img src={companyLogo} alt="Logo" className="h-24" />
            Meals&nbsp;on&nbsp;<span className="text-black">Wheels</span>
          </div>

          {/* Navigation */}
          <nav className="flex-1 flex justify-center  lg:ml-[-5px]">
            <ul className="flex space-x-8">
              <li>
                <NavLink to="/" className="text-black hover:text-white">Home</NavLink>
              </li>
              <li>
                <NavLink to="/menu" className="text-white hover:text-black">Menu</NavLink>
              </li>
              <li>
                <NavLink to="/about" className="text-white hover:text-black">About</NavLink>
              </li>
              <li>
                <NavLink to="/contact" className="text-white hover:text-black">Contact</NavLink>
              </li>
            </ul>
          </nav>

          <div className="flex space-x-4 lg:mr-4">
            <div className="flex space-x-4 ml-4 lg:mr-5">
              {isLoggedIn ? (
                <button
                  onClick={handleLogout}
                  className="bg-red-700 text-white border border-white px-6 py-2 rounded-lg hover:bg-white hover:text-red-700 transition">
                  Logout
                </button>
              ) : (
                <>
                  <NavLink
                    to="/login"
                    className="bg-red-700 text-white border border-white px-6 py-2 rounded-lg hover:bg-white hover:text-red-700 transition">
                    Login
                  </NavLink>
                  <NavLink
                    to="/register"
                    className="text-white px-4 py-2">
                    Register
                  </NavLink>
                </>
              )}
            </div>       
          </div>
        </div>

        {/* 🚧 Temporary Dev Button Change for Different userType*/}
        <div className="flex justify-center mt-2">
          <button
            onClick={() => {
              localStorage.setItem("userType", "admin");
              localStorage.setItem("userName", "TestAdmin");
              localStorage.setItem("token", "dummyToken");
              window.location.href = "/admin";
            }}
            className="bg-yellow-500 text-black font-bold px-4 py-2 rounded hover:bg-yellow-400 transition"
          >
            Set Admin and Go
          </button>
        </div>
      </div>
    </header>
  );
}

