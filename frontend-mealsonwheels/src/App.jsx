import { useState } from 'react';
import './App.css';
import HeaderNavbar from './components/HeaderNavbar.jsx'
import Footer from './components/Footer.jsx';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Admin pages
import Dashboard from './pages/admin/Dashboard';
import MembersAndCaregivers from './pages/admin/MembersAndCaregivers';
import CompanyPartners from './pages/admin/CompanyPartners';
import CompanyVolunteers from './pages/admin/CompanyVolunteers';
import MemberFeedbacks from './pages/admin/MemberFeedbacks';
import MenuDescriptions from './pages/admin/MenuDescriptions';
import ManageDeliveries from './pages/admin/ManageDeliveries';
import AdminInfo from './pages/admin/AdminInfo';

// Other roles
import MemberDashboard from './pages/Member/MemberDashboard';
import PartnerDashboard from './pages/partner/partnerdashboard';
import VolunteerDashboard from './pages/Volunteer/VolunteerDashboard';

// Member pages
import Member_ConfirmOrder from './pages/Member/MemberConfirmOrder'; 
import Member_MealOrder from './pages/Member/MemberMealOrder';
import MemberFeedback from './pages/Member/MemberFeedback'; // Make sure this exists

// Partner pages
import PartnerAddMenu from './pages/Partner/PartnerAddMenu';
import PartnersFoodSafety from './pages/partner/PartnersFoodSafety'; // Make sure this exists
import PartnersFoodSafety from './pages/partner/PartnersFoodSafety'; // Make sure this exists

// Volunteer pages
import VolunteerDeliveryStatus1 from './pages/Volunteer/VolunteerDeliveryStatus1';
import VolunteerDeliveryStatus2 from './pages/Volunteer/VolunteerDeliveryStatus2';
import RegisterVolunteer from './pages/Volunteer/RegisterVolunteer'; // Make sure this exists

// Auth and testing
import Login from './pages/auth/Login.jsx';
import Register from './pages/auth/Register';
import TestConnection from './pages/TestConnection';
import RegistrationSuccessPage from './pages/auth/RegisterSuccessPage';
import ApprovalPendingPage from './pages/auth/ApprovalPendingPage.jsx';

import ProtectedRoute from './components/ProtectedRoute';
import MenuPlanningPage from './pages/partner/MenuPlanning';
import Contact from './pages/Contact';

// Public 
import Home from './pages/Home.jsx';

// Share Pages
import RedirectFoodSafety from './pages/Shared/RedirectFoodSafety';

function App() {
  return (
    <Router>
      
      <HeaderNavbar />

      <Routes>

        {/* Admin routes */}
        <Route path="/admin/dashboard" element={<ProtectedRoute allowedRole="admin"><Dashboard /></ProtectedRoute>} />
        <Route path="/admin/members" element={<ProtectedRoute allowedRole="admin"><MembersAndCaregivers /></ProtectedRoute>} />
        <Route path="/admin/partners" element={<ProtectedRoute allowedRole="admin"><CompanyPartners /></ProtectedRoute>} />
        <Route path="/admin/volunteers" element={<ProtectedRoute allowedRole="admin"><CompanyVolunteers /></ProtectedRoute>} />
        <Route path="/admin/feedbacks" element={<ProtectedRoute allowedRole="admin"><MemberFeedbacks /></ProtectedRoute>} />
        <Route path="/admin/menus" element={<ProtectedRoute allowedRole="admin"><MenuDescriptions /></ProtectedRoute>} />
        <Route path="/admin/deliveries" element={<ProtectedRoute allowedRole="admin"><ManageDeliveries /></ProtectedRoute>} />
        <Route path="/admin/info" element={<ProtectedRoute allowedRole="admin"><AdminInfo /></ProtectedRoute>} />

        {/* Member routes */}
        <Route path="/member" element={<ProtectedRoute allowedRole="member"><MemberDashboard /></ProtectedRoute>} />
        <Route path="/member/meal-order" element={<ProtectedRoute allowedRole="member"><Member_MealOrder /></ProtectedRoute>} />
        <Route path="/member/confirm-order" element={<ProtectedRoute allowedRole="member"><Member_ConfirmOrder /></ProtectedRoute>} />
        <Route path="/member/memberfeedback" element={<ProtectedRoute allowedRole="member"><MemberFeedback /></ProtectedRoute>} />
        <Route path="/member/foodsafety" element={<ProtectedRoute allowedRole="member"><PartnersFoodSafety /></ProtectedRoute>} />
        
        {/* Shared pages */}
        <Route path="/food-safety" element={<RedirectFoodSafety />} />

        {/* Partner routes */}
        <Route path="/partner/dashboard" element={<ProtectedRoute allowedRole="partner"><PartnerDashboard /></ProtectedRoute>} />
        <Route path="/partner/MenuPlanning" element={<ProtectedRoute allowedRole="partner"><MenuPlanningPage /></ProtectedRoute>} />
        <Route path="/partner/create-menu" element={<ProtectedRoute allowedRole="partner"><PartnerAddMenu /></ProtectedRoute>} />
        <Route path="/partner/partnerdashboard" element={<ProtectedRoute allowedRole="partner"><PartnerDashboard /></ProtectedRoute>} />
        <Route path="/partner/add-menu" element={<ProtectedRoute allowedRole="partner"><PartnerAddMenu /></ProtectedRoute>} />
        <Route path="/partner/partnersfoodsafety" element={<ProtectedRoute allowedRole="partner"><PartnersFoodSafety /></ProtectedRoute>} />

        {/* Volunteer routes */}
        <Route path="/volunteer" element={<ProtectedRoute allowedRole="volunteer"><VolunteerDashboard /></ProtectedRoute>} />
        <Route path="/volunteer/delivery-status1" element={<ProtectedRoute allowedRole="volunteer"><VolunteerDeliveryStatus1 /></ProtectedRoute>} />
        <Route path="/volunteer/delivery-status2" element={<ProtectedRoute allowedRole="volunteer"><VolunteerDeliveryStatus2 /></ProtectedRoute>} />
        <Route path="/volunteer/registervolunteer" element={<ProtectedRoute allowedRole="volunteer"><RegisterVolunteer /></ProtectedRoute>} />

        {/* Auth and utility routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/test" element={<TestConnection />} />
        <Route path="/register/success" element={<RegistrationSuccessPage />} />
        <Route path='/login/approval' element={<ApprovalPendingPage />} />

        {/* Public routes */}
        <Route path="/contact" element={<Contact />} />

        {/* Home route */}
        <Route path="/" element={
          <Home />
        } />

        {/* Home route 
        <Route path="/" element={
          <div className="text-3xl font-bold underline p-8">
            Welcome to the home page!
            <br />
            <a href="/test" className="text-blue-600 hover:text-blue-800 text-lg">
              🔧 Test Backend Connection
            </a>
          </div>
        } /> */}
      </Routes>
      <Footer />
    </Router>
  );
}

export default App;
