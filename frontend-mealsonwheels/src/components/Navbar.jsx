import React from "react";
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
    { to: "/dashboard", label: "Dashboard" },
    { to: "/manageUsers", label: "Manage Users" },
    { to: "/manageMenus", label: "Manage Menus" },
    { to: "/manageDeliveries", label: "Manage Deliveries" },
    { to: "/contact", label: "Contact" },
  ],
};

export function Navbar() {
  const userRole = localStorage.getItem("userType");
  const userName = localStorage.getItem("userName");

  const defaultNav = [
    { to: "/donate", label: "Donate" },
    { to: "/volunteer", label: "Volunteer" },
    { to: "/menus", label: "Menus" },
    { to: "/partner", label: "Partner" },
    { to: "/contact", label: "Contact" },
  ];

  const navItems = navItemsByRole[userRole] || defaultNav;
  const isLoggedIn = Boolean(userRole && userName);

  return (
    <header className="bg-indigo-950 text-white w-full shadow-md tracking-wide font-medium">
      <div className="max-w-8xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <nav className="flex mx-2">
            <ul className="flex space-x-6">
              {navItems.map(({ to, label }) => (
                <li key={to}>
                  <NavLink to={to} className="mx-4 hover:underline">
                    {label}
                  </NavLink>
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