// src/components/ProtectedRoute.jsx
/*
import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ allowedRole, children }) => {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("userType");

  if (!token || role !== allowedRole) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
*/
import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ allowedRole, children }) => {
  const token = localStorage.getItem("token");
  const rawRole = localStorage.getItem("userType");

  // Normalize: remove 'ROLE_' if present, and lowercase it
  const userRole = rawRole?.replace("ROLE_", "").toLowerCase();
  const allowed = allowedRole?.toLowerCase();

  if (!token || userRole !== allowed) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
