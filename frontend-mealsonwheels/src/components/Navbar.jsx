import React, { useState } from "react";
import { NavLink } from "react-router-dom";

const navItemsByRole = {
  volunteer: [
    { to: "/dashboard", label: "Dashboard" },
    { to: "/deliveries", label: "Deliveries" },
    { to: "/menus", label: "Menus" },
    { to: "/contact", label: "Contact" },
  ],
  partner: [
    { to: "/dashboard", label: "Dashboard" },
    { to: "/deliveries", label: "Deliveries" },
    { to: "/reassessment", label: "Reassessment" },
    { to: "/menu", label: "Menu" },
    { to: "/contact", label: "Contact" },
  ],
  member: [
    { to: "/dashboard", label: "Dashboard" },
    { to: "/orders", label: "Orders" },
    { to: "/menu", label: "Menu" },
    { to: "/deliveryHistory", label: "Delivery History" },
    { to: "/contact", label: "Contact" },
  ],
  admin: [
    { to: "/admin/dashboard", label: "Dashboard" },
    {
      label: "Manage Users",
      dropdown: [
        { to: "/admin/members", label: "Member and Caregivers" },
        { to: "/admin/partners", label: "Partners" },
        { to: "/admin/volunteers", label: "Volunteers" },
        { to: "/admin/feedbacks", label: "Feedbacks" },
      ],
    },
    { to: "/admin/menus", label: "Manage Menus" },
    { to: "/admin/deliveries", label: "Manage Deliveries" },
    { to: "/admin/contact", label: "Contact" },
  ],
};

export function Navbar() {
  const userRole = localStorage.getItem("userType")?.toLowerCase() || null;
  const userName = localStorage.getItem("userName");
  const isLoggedIn = Boolean(userRole && userName);

  const navItems = navItemsByRole[userRole] || [
    { to: "/donate", label: "Donate" },
    { to: "/volunteer", label: "Volunteer" },
    { to: "/menus", label: "Menus" },
    { to: "/partner", label: "Partner" },
    { to: "/contact", label: "Contact" },
  ];

  const [openDropdown, setOpenDropdown] = useState(null);

  const toggleDropdown = (label) => {
    setOpenDropdown((prev) => (prev === label ? null : label));
  };

  return (
    <header className="bg-indigo-950 text-white w-full shadow-md tracking-wide font-medium">
      <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <nav className="flex mx-2">
            <ul className="flex space-x-6">
              {navItems.map((item) => (
                <li key={item.label} className="relative">
                  {item.dropdown ? (
                    <>
                      <button
                        type="button"
                        onClick={() => toggleDropdown(item.label)}
                        aria-haspopup="true"
                        aria-expanded={openDropdown === item.label}
                        className="mx-4 hover:underline focus:outline-none"
                      >
                        {item.label}
                      </button>
                      {openDropdown === item.label && (
                        <ul className="absolute left-0 mt-2 w-56 bg-white text-black shadow-lg rounded-lg z-50">
                          {item.dropdown.map((subItem) => (
                            <li key={subItem.to}>
                              <NavLink
                                to={subItem.to}
                                className={({ isActive }) =>
                                  `block px-4 py-2 hover:bg-gray-200 ${
                                    isActive
                                      ? "font-semibold bg-gray-100"
                                      : ""
                                  }`
                                }
                                onClick={() => setOpenDropdown(null)} // close when clicked
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
                      className={({ isActive }) =>
                        `mx-auto hover:underline ${
                          isActive
                            ? "underline font-bold text-yellow-400"
                            : ""
                        }`
                      }
                      onClick={() => setOpenDropdown(null)} // close dropdown when clicking other nav item
                    >
                      {item.label}
                    </NavLink>
                  )}
                </li>
              ))}
            </ul>
          </nav>

          <div className="flex space-x-4 lg:mr-4">
            {isLoggedIn ? (
              <div>
                <span className="font-semibold mr-2">{userName}</span>
                <span className="italic text-gray-300">{userRole}</span>
              </div>
            ) : (
              <NavLink to="/privacyPolicy" className="hover:underline">
                Privacy Policy
              </NavLink>
            )}
          </div>
        </div>
      </div>
    </header>
  );
}
