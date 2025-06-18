<<<<<<< Updated upstream
// import React from "react";
// import { Navigate } from "react-router-dom";

// const ProtectedRoute = ({ allowedRole, children}) => {
//     const userRole = localStorage.getItem("userType");

//     if (userRole !== allowedRole) {
//         return <Navigate to="/login" replace />;
//     }
//     return children;
// }

// export default ProtectedRoute;
=======
// src/components/ProtectedRoute.jsx
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
>>>>>>> Stashed changes
