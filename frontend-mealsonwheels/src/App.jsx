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
import ManageCaregiverDeliveries from './pages/admin/ManageCaregiverDeliveries.jsx';

// Other roles
import MemberDashboard from './pages/Member/MemberDashboard';
import PartnerDashboard from './pages/Partner/PartnerDashboard';
import VolunteerDashboard from './pages/Volunteer/VolunteerDashboard';

// Member pages
import Member_ConfirmOrder from './pages/Member/MemberConfirmOrder'; 
import Member_MealOrder from './pages/Member/MemberMealOrder';
import MemberFeedback from './pages/Member/MemberFeedback'; // Make sure this exists
import OrderSuccessPage from './pages/Member/OrderSuccessPage.jsx';
import MealHistory from './pages/Member/MealHistory.jsx';
import OrderTracking from './pages/Member/OrderTracking.jsx';

// Partner pages
import PartnerAddMenu from './pages/Partner/PartnerAddMenu';
import PartnersFoodSafety from './pages/partner/PartnersFoodSafety'; // Make sure this exists

// Volunteer pages
import VolunteerDeliveryStatus1 from './pages/Volunteer/VolunteerDeliveryStatus1';
import VolunteerDeliveryStatus2 from './pages/Volunteer/VolunteerDeliveryStatus2';
import RegisterVolunteer from './pages/Volunteer/RegisterVolunteer'; // Make sure this exists

// Rider pages
import RiderDeliveryStatus from './pages/Rider/DeliveryStatus.jsx';

// Auth and testing
import Login from './pages/auth/Login.jsx';
import Register from './pages/auth/Register';
import TestConnection from './pages/TestConnection';
import RegistrationSuccessPage from './pages/auth/RegisterSuccessPage';
import ApprovalPendingPage from './pages/auth/ApprovalPendingPage.jsx';

import ProtectedRoute from './components/ProtectedRoute';
//import MenuPlanningPage from './pages/partner/MenuPlanning';
import Contact from './pages/Contact';

// Public 
import Home from './pages/Home.jsx';

// Donation
import DonateAmountPage from './pages/Donation/DonateAmountPage.jsx';
import BillingDetails from './pages/Donation/BillingDetails.jsx';
import DonatePayment from './pages/Donation/DonatePayment.jsx'
import DonateComplete from './pages/Donation/DonationCompletion.jsx'

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
        <Route path="/admin/riders" element={<ProtectedRoute allowedRole="admin"><CompanyVolunteers /></ProtectedRoute>} />
        <Route path="/admin/feedbacks" element={<ProtectedRoute allowedRole="admin"><MemberFeedbacks /></ProtectedRoute>} />
        <Route path="/admin/menus" element={<ProtectedRoute allowedRole="admin"><MenuDescriptions /></ProtectedRoute>} />
        <Route path="/admin/member-deliveries" element={<ProtectedRoute allowedRole="admin"><ManageDeliveries /></ProtectedRoute>} />
        <Route path="/admin/info" element={<ProtectedRoute allowedRole="admin"><AdminInfo /></ProtectedRoute>} />
        <Route path="/admin/caregiver-deliveries" element={<ManageCaregiverDeliveries />} />

        {/* Member and Caregiver routes */}
        <Route path="/member/dashboard" element={<ProtectedRoute allowedRole="member"><MemberDashboard /></ProtectedRoute>} />
        <Route path="/caregiver/dashboard" element={<ProtectedRoute allowedRole="caregiver"><MemberDashboard /></ProtectedRoute>} />

        <Route path="/member/menus" element={<ProtectedRoute allowedRole="member"><Member_MealOrder /></ProtectedRoute>} />
        <Route path="/caregiver/menus" element={<ProtectedRoute allowedRole="caregiver"><Member_MealOrder /></ProtectedRoute>} />

        <Route path="/member/confirm-order" element={<ProtectedRoute allowedRole="member"><Member_ConfirmOrder /></ProtectedRoute>} />
        <Route path="/caregiver/confirm-order" element={<ProtectedRoute allowedRole="caregiver"><Member_ConfirmOrder /></ProtectedRoute>} />

        <Route path="/member/feedback/:orderId" element={<MemberFeedback />} />
        <Route path="/caregiver/feedback/:orderId" element={<MemberFeedback />} />

        <Route path="/member/foodsafety" element={<ProtectedRoute allowedRole="member"><PartnersFoodSafety /></ProtectedRoute>} />
        <Route path="/caregiver/foodsafety" element={<ProtectedRoute allowedRole="caregiver"><PartnersFoodSafety /></ProtectedRoute>} />

        <Route path="/member/order-success" element={<ProtectedRoute allowedRole="member"><OrderSuccessPage /> </ProtectedRoute>} />
        <Route path="/caregiver/order-success" element={<ProtectedRoute allowedRole="caregiver"><OrderSuccessPage /> </ProtectedRoute>} />

        <Route path="/member/history" element={<ProtectedRoute allowedRole="member"><MealHistory /></ProtectedRoute>} />
        <Route path="/caregiver/history" element={<ProtectedRoute allowedRole="caregiver"><MealHistory /></ProtectedRoute>} />

        <Route path="/member/track-orders" element={<ProtectedRoute allowedRole="member"><OrderTracking /></ProtectedRoute>} />
        <Route path="/caregiver/track-orders" element={<ProtectedRoute allowedRole="caregiver"><OrderTracking /></ProtectedRoute>} />
         
        {/* Shared pages */}
        <Route path="/food-safety" element={<RedirectFoodSafety />} />

        {/* Partner routes */}
        <Route path="/partner/dashboard" element={<ProtectedRoute allowedRole="partner"><PartnerDashboard /></ProtectedRoute>} />
        {/*<Route path="/partner/menuplanning" element={<ProtectedRoute allowedRole="partner"><MenuPlanningPage /></ProtectedRoute>} />*/}
        <Route path="/partner/create-menu" element={<ProtectedRoute allowedRole="partner"><PartnerAddMenu /></ProtectedRoute>} />
        <Route path="/partner/add-menu" element={<ProtectedRoute allowedRole="partner"><PartnerAddMenu /></ProtectedRoute>} />
        <Route path="/partner/partnersfoodsafety" element={<ProtectedRoute allowedRole="partner"><PartnersFoodSafety /></ProtectedRoute>} />

        {/* Volunteer routes */}
        <Route path="/volunteer/dashboard" element={<ProtectedRoute allowedRole="volunteer"><VolunteerDashboard /></ProtectedRoute>} />
        <Route path="/volunteer/delivery-status1" element={<ProtectedRoute allowedRole="volunteer"><VolunteerDeliveryStatus1 /></ProtectedRoute>} />
        <Route path="/volunteer/delivery-status2" element={<ProtectedRoute allowedRole="volunteer"><VolunteerDeliveryStatus2 /></ProtectedRoute>} />
        <Route path="/volunteer/registervolunteer" element={<ProtectedRoute allowedRole="volunteer"><RegisterVolunteer /></ProtectedRoute>} />

        {/* Rider routes */}
        <Route path="/rider/delivery-status" element={<ProtectedRoute allowedRole="rider"><RiderDeliveryStatus /></ProtectedRoute>} />
        
        {/* Auth and utility routes */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/test" element={<TestConnection />} />
        <Route path="/register/success" element={<RegistrationSuccessPage />} />
        <Route path='/login/approval' element={<ApprovalPendingPage />} />

        {/* Public routes */}
        <Route path="/contact" element={<Contact />} />

        {/* Donation routes */}
        <Route path='/donate' element={<DonateAmountPage/>} />
        <Route path="/donate/billing" element={<BillingDetails />} />
        <Route path="/donate/payment" element={<DonatePayment />} />
        <Route path='/donate/complete' element={<DonateComplete />} />

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
