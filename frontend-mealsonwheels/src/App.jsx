//import { useState } from 'react'
import './App.css';
import { Header } from './components/Header';
import { Navbar } from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Imported Pages

import AdminDashboard from './pages/AdminDashboard';
import MemberDashboard from './pages/MemberDashboard';
import PartnerDashboard from './pages/PartnerDashboard';
import VolunteerDashboard from './pages/VolunteerDashboard';

import Login from './pages/Login';
import Register from './pages/Register';

// Import more if needed

import ProtectedRoute from './components/ProtectedRoute';
//Member pages 
import Member_ConfirmOrder from './pages/Member/MemberConfirmOrder'; 
import Member_MealOrder from './pages/Member/MemberMealOrder';
import VolunteerDeliveryStatus1 from './pages/Volunteer/VolunteerDeliveryStatus1';
import VolunteerDeliveryStatus2 from './pages/Volunteer/VolunteerDeliveryStatus2';
import PartnerAddMenu from './pages/Partner/PartnerAddMenu';

function App() {
    //const [isAdmin, setAdmin] = useState(true); // Replace this with real auth logic
    const userRole = localStorage.getItem("userType"); // fake auth erase after developmebt
    const isAdmin = userRole === "admin"; // fake auth to make the pages work

  return (
    <Router>
      <Header />
      <Navbar />

        <Routes>
          

          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />
          
          <Route path="/admin" element={
            <ProtectedRoute allowedRole="admin">
              <AdminDashboard />
            </ProtectedRoute>
          } />

          <Route path="/member" element={
            <ProtectedRoute allowedRole="member">
              <MemberDashboard />
            </ProtectedRoute>
          } />

          <Route path="/partner" element={
            <ProtectedRoute allowedRole="partner">
              <PartnerDashboard />
            </ProtectedRoute>
          } />

          <Route path="/volunteer" element={
            <ProtectedRoute allowedRole="volunteer">
              <VolunteerDashboard />
            </ProtectedRoute>
          } />

          <Route path="/member/meal-order" element={
            <ProtectedRoute allowedRole="member">
              <Member_MealOrder />
            </ProtectedRoute>
          } />

          <Route path="/member/confirm-order" element={
            <ProtectedRoute allowedRole="member">
              <Member_ConfirmOrder />
            </ProtectedRoute>
          } />  

          <Route path="/volunteer/delivery-status1" element={
            <ProtectedRoute allowedRole="volunteer">
              <VolunteerDeliveryStatus1 />
            </ProtectedRoute>
          } />

          <Route path="/volunteer/delivery-status2" element={
            <ProtectedRoute allowedRole="volunteer">
              <VolunteerDeliveryStatus2 />
            </ProtectedRoute>
          } />
          
          <Route path="/partner/add-menu" element={
            <ProtectedRoute allowedRole="partner">
              <PartnerAddMenu />
            </ProtectedRoute>
          } />

          {/* Add more routes as needed */}

          <Route path="/" element={<div className="text-3xl font-bold underline">Welcome to the home page!</div>} />
        </Routes>

      
    </Router>
      
  );
}
export default App;
