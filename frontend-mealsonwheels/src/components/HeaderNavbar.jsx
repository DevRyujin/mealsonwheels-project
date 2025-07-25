import React, { useState, useEffect, useRef } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import logo from "/images/logo.png";

const navItemsByRole = {
  admin: [
    { to: "/admin/dashboard", label: "Dashboard" },
    {
      label: "Manage Users",
      dropdown: [
        { to: "/admin/members", label: "Member and Caregivers" },
        { to: "/admin/partners", label: "Partners" },
        { to: "/admin/riders", label: "Riders" },
        { to: "/admin/feedbacks", label: "Feedbacks" },
      ],
    },
    { to: "/admin/menus", label: "Manage Menus" },
    { 
      label: "Manage Deliveries",
      dropdown: [
        { to: "/admin/member-deliveries", label: "Member Deliveries" },
        { to: "/admin/caregiver-deliveries", label: "Caregiver Deliveries"},
      ],
    },
    { to: "/admin/info", label: "Admin Info" },
  ],
  member: [
    { to: "/member/dashboard", label: "Dashboard" },
    { to: "/member/membercaregiver", label: "Caregiver" },
    { to: "/member/track-orders", label: "Track Orders" },
    { to: "/member/menus", label: "Menu" },
    { to: "/member/history", label: "History" },
  ],
  caregiver: [
    { to: "/caregiver/dashboard", label: "Dashboard" },
    { to: "/caregiver/member-profile", label: "Assigned Member" },
    { to: "/caregiver/track-orders", label: "Track Orders" },
    { to: "/caregiver/menus", label: "Menu" },
    { to: "/caregiver/history", label: "History" },
  ],
  partner: [
    { to: "/partner/dashboard", label: "Dashboard" },
    { to: "/partner/reassessment", label: "Reassessment" },
    { to: "/partner/create-menu", label: "Create Menu" },
  ],
  volunteer: [
    { to: "/volunteer/dashboard", label: "Dashboard" },
    { to: "/volunteer/delivery-status1", label: "Deliveries 1" },
    { to: "/volunteer/delivery-status2", label: "Deliveries 2" },
    { to: "/volunteer/menus", label: "Menus" },
    { to: "/volunteer/contact", label: "Contact" },
  ],
  rider: [
    { to: "/rider/dashboard", label: "Dashboard" },
    { to: "/rider/delivery-status", label: "Deliveries" },
  ]
};

export default function HeaderNavbar() {
  const navigate = useNavigate();
  const role = localStorage.getItem("userType")?.toLowerCase() || null;

  const userName = localStorage.getItem("userName");
  
  const isLoggedIn = Boolean(localStorage.getItem("token"));
  const userRole = role;
  const navItems =
    navItemsByRole[role] || [
      { to: "/donate", label: "Donate" },
      { to: "/menus", label: "Menus" },
      { to: "/volunteerpage", label: "Volunteer" },
      { to: "/partnerpage", label: "Partner" },
      { to: "/foodsafety", label: "Food Safety" }
    ];

  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [openDropdown, setOpenDropdown] = useState(null);
  const dropdownRef = useRef();

  const toggleDropdown = (label) =>
    setOpenDropdown((prev) => (prev === label ? null : label));

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };


  useEffect(() => {
    const handler = (e) => {
      if (dropdownRef.current && !dropdownRef.current.contains(e.target)) {
        setOpenDropdown(null);
      }
    };
    document.addEventListener("mousedown", handler);
    return () => document.removeEventListener("mousedown", handler);
  }, []);


  return (
    <>
      {/* HEADER */}
      <header className="bg-red-700 text-white w-full shadow-md tracking-widest font-medium">
        <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-20">
            {/* Logo and Brand */}
            <div className="flex items-center text-xl font-bold tracking-wide">
              <img src={logo} alt="Logo" className="h-15" />
              Meals&nbsp;on&nbsp;<span className="text-black">Wheels</span>
            </div>

            {/* Desktop Nav */}
            <nav className="hidden md:flex flex-1 justify-center  ">
              <ul className="flex space-x-8 text-md">
                <li><NavLink to="/" className="no-underline px-2 py-1 hover:text-indigo-900 transition">Home</NavLink></li>
                <li><NavLink to="/about" className="no-underline px-2 py-1 hover:text-indigo-900 transition">About</NavLink></li>
                <li><NavLink to="/services" className="no-underline px-2 py-1 hover:text-indigo-900 transition">Services</NavLink></li>
                <li><NavLink to="/contact" className="no-underline px-2 py-1 hover:text-indigo-900 transition">Contact</NavLink></li>
              </ul>
            </nav>

            {/* Auth Buttons */}
            <div className="hidden md:flex items-center space-x-4 mx-10">
              {isLoggedIn ? (
                <>
                  <button
                    onClick={handleLogout}
                    className="bg-white text-red-700 px-4 py-2 rounded hover:bg-red-800 hover:text-white transition"
                  >
                    Logout
                  </button>
                </>
              ) : (
                <>
                  <NavLink to="/login" className="no-underline px-2 py-1 hover:text-indigo-900 transition">Login</NavLink>
                  <NavLink to="/register" className="no-underline px-2 py-1 hover:text-indigo-900 transition">Register</NavLink>
                </>
              )}
            </div>

            {/* Hamburger Icon */}
            <button
              className="md:hidden flex flex-col space-y-1 z-50"
              onClick={() => setSidebarOpen(!sidebarOpen)}
            >
              <div className="w-6 h-1 bg-white" />
              <div className="w-6 h-1 bg-white" />
              <div className="w-6 h-1 bg-white" />
            </button>
          </div>
        </div>
      </header>

      {/* MOBILE SIDEBAR */}
        <div
        className={`fixed top-0 left-0 h-full w-64 bg-indigo-900 text-white z-40 p-6 transform transition-transform duration-300 ${
            sidebarOpen ? "translate-x-0" : "-translate-x-full"
        }`}
        >
        {/* Close button */}
        <button
            onClick={() => setSidebarOpen(false)}
            className="absolute top-4 right-4 text-white text-3xl"
        >
            &times;
        </button>

        {/* USER INFO (Top) */}
        {isLoggedIn && (
            <div className="mt-16 mb-4">
            <p className="font-bold text-lg">{userName}</p>
            <p className="text-sm text-gray-300 capitalize">{userRole}</p>
            </div>
        )}

        {/* PUBLIC LINKS */}
        <ul className="space-y-3 border-b border-gray-600 pb-4">
            {[
            { to: "/", label: "Home" },
            { to: "/about", label: "About" },
            { to: "/services", label: "Services" },
            { to: "/contact", label: "Contact" },
            ].map((item) => (
            <li key={item.to}>
                <NavLink
                to={item.to}
                onClick={() => setSidebarOpen(false)}
                className="block px-2 py-1 rounded transition hover:bg-indigo-700 hover:text-yellow-300 no-underline"
                >
                {item.label}
                </NavLink>
            </li>
            ))}
        </ul>

        {/* ROLE-BASED LINKS */}
        <ul className="mt-4 space-y-3">
            {navItems.map((item) =>
            item.dropdown ? (
                <li key={item.label}>
                <button
                    onClick={() => toggleDropdown(item.label)}
                    className="w-full text-left px-2 py-1 rounded hover:bg-indigo-700 hover:text-yellow-300 flex items-center justify-between"
                >
                    <span>{item.label}</span>
                    <span
                    className={`transform transition-transform duration-500 ${
                        openDropdown === item.label ? "rotate-180" : ""
                    }`}
                    >
                    ▼
                    </span>
                </button>
                {openDropdown === item.label && (
                    <ul className="ml-4 mt-2 space-y-2 text-sm">
                    {item.dropdown.map((subItem) => (
                        <li key={subItem.to}>
                        <NavLink
                            to={subItem.to}
                            onClick={() => {
                            setSidebarOpen(false);
                            setOpenDropdown(null);
                            }}
                            className="block px-2 py-1 rounded transition hover:bg-indigo-600 hover:text-yellow-300 no-underline"
                        >
                            {subItem.label}
                        </NavLink>
                        </li>
                    ))}
                    </ul>
                )}
                </li>
            ) : (
                <li key={item.to}>
                <NavLink
                    to={item.to}
                    onClick={() => setSidebarOpen(false)}
                    className="block px-2 py-1 rounded transition hover:bg-indigo-700 hover:text-yellow-300 no-underline"
                >
                    {item.label}
                </NavLink>
                </li>
            )
        )}
        </ul>

        {/* FOOTER: Logout / Privacy */}
        <div className="mt-auto absolute bottom-6 left-6 right-6">
            {isLoggedIn ? (
            <button
                onClick={handleLogout}
                className="w-full bg-red-600 py-2 rounded hover:bg-red-800 transition"
            >
                Logout
            </button>
            ) : (
            <NavLink
                to="/privacyPolicy"
                onClick={() => setSidebarOpen(false)}
                className="text-sm text-gray-300 hover:text-white no-underline hover:underline"
            >
                Privacy Policy
            </NavLink>
            )}
        </div>
        </div>



        {/* ROLE NAVBAR (Desktop only) */}
        <nav className="hidden md:block bg-indigo-950 text-white shadow-md">
        <div className="max-w-8xl mx-auto px-4">
            <div className="flex justify-between items-center h-14">
                <ul className="flex space-x-6">
                    {navItems.map((item) => (
                    <li key={item.label} className="relative">
                        {item.dropdown ? (
                        <>
                            <button
                            onClick={() => toggleDropdown(item.label)}
                            className="hover:no-underline focus:outline-none flex items-center space-x-1"
                            >
                            <span className="flex items-center space-x-1">
                                <span>{item.label}</span>
                                <span
                                className={`transform transition-transform duration-500 ${
                                    openDropdown === item.label ? "rotate-180" : ""
                                }`}
                                >
                                ▼
                                </span>
                            </span>
                            </button>
                            {openDropdown === item.label && (
                            <ul className="absolute left-0 mt-2 w-56 bg-white text-black shadow-lg rounded-lg z-50">
                                {item.dropdown.map((subItem) => (
                                <li key={subItem.to}>
                                    <NavLink
                                    to={subItem.to}
                                    className="block px-4 py-2 hover:bg-gray-200"
                                    onClick={() => setOpenDropdown(null)}
                                    >
                                    {subItem.label}
                                    </NavLink>
                                </li>
                                ))}
                            </ul>
                            )}
                        </>
                        ) : (
                        <NavLink
                            to={item.to}
                            className="no-underline px-2 py-1 hover:text-red-700 transition"
                            onClick={() => setOpenDropdown(null)}
                        >
                            {item.label}
                        </NavLink>
                        )}
                    </li>
                    ))}
                </ul>

                <div className="flex space-x-4">
                    {isLoggedIn ? (
                    <div>
                        <span className="font-semibold mr-2">{userName}</span>
                        <span className="italic text-gray-300 capitalize">{userRole}</span>
                    </div>
                    ) : (
                    <NavLink
                        to="/privacyPolicy"
                        className="no-underline px-2 py-1 hover:text-red-700 transition"
                    >
                        Privacy Policy
                    </NavLink>
                    )}
                </div>
            </div>
        </div>
        </nav>
    </>
  );
}
