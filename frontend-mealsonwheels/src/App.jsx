import { useState } from 'react'
import './App.css';
import { Header } from './components/Header';
import { Navbar } from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Imported Pages
<<<<<<< Updated upstream
import AdminDashboard from './pages/AdminDashboard';
=======
// Admin pages
import Dashboard from './pages/admin/Dashboard';
import MembersAndCaregivers from './pages/admin/MembersAndCaregivers';
import CompanyPartners from './pages/admin/CompanyPartners';
import CompanyVolunteers from './pages/admin/CompanyVolunteers';
import MemberFeedbacks from './pages/admin/MemberFeedbacks';
import MenuDescriptions from './pages/admin/MenuDescriptions';
import ManageDeliveries from './pages/admin/ManageDeliveries';
import AdminInfo from './pages/admin/AdminInfo';


// Member pages
>>>>>>> Stashed changes
import MemberDashboard from './pages/MemberDashboard';

// Partner pages
import PartnerDashboard from './pages/PartnerDashboard';

// Volunteer pages
import VolunteerDashboard from './pages/VolunteerDashboard';

// Authentication pages
import Login from './pages/Login';
import Register from './pages/Register';
import TestConnection from './pages/TestConnection'; // Add this import

<<<<<<< Updated upstream
// import ProtectedRoute from './components/ProtectedRoute';

function App() {
=======
// Security page
import ProtectedRoute from './components/ProtectedRoute';


function App() {
    //const [isAdmin, setAdmin] = useState(true); // Replace this with real auth logic
    const userRole = localStorage.getItem("userType"); // fake auth erase after development
    const isAdmin = userRole === "admin"; // fake auth to make the pages work

>>>>>>> Stashed changes
  return (
    <Router>
      <Header />
      <Navbar />

<<<<<<< Updated upstream
      <Routes>
        <Route path='/login' element={<Login />} />
        <Route path='/register' element={<Register />} />
        <Route path='/test' element={<TestConnection />} /> {/* Add this route */}

        <Route path="/" element={
          <div className="text-3xl font-bold underline p-8">
            Welcome to the home page!
            <br />
            <a href="/test" className="text-blue-600 hover:text-blue-800 text-lg">
              🔧 Test Backend Connection
            </a>
          </div>
        } />
      </Routes>
=======
        <Routes>
          
          <Route path='/login' element={<Login />} />
          <Route path='/register' element={<Register />} />

          {/* This is for Admin routing */}
          <Route path="/admin/dashboard" element={
            <ProtectedRoute allowedRole="admin">
              <Dashboard />
            </ProtectedRoute>
          } />
          
          <Route path='/admin/members' element={
            <ProtectedRoute allowedRole="admin">
              <MembersAndCaregivers />
            </ProtectedRoute>
          } />

          <Route path='/admin/partners' element={
            <ProtectedRoute allowedRole="admin">
              <CompanyPartners />
            </ProtectedRoute>
          } />

          <Route path='/admin/volunteers' element={
            <ProtectedRoute allowedRole="admin">
              <CompanyVolunteers />
            </ProtectedRoute>
          } />

          <Route path='/admin/feedbacks' element={
            <ProtectedRoute allowedRole="admin">
              <MemberFeedbacks />
            </ProtectedRoute>
          } />

          <Route path='/admin/menus' element={
            <ProtectedRoute allowedRole="admin">
              <MenuDescriptions />
            </ProtectedRoute>
          } />

          <Route path='/admin/deliveries' element={
            <ProtectedRoute allowedRole="admin">
              <ManageDeliveries />
            </ProtectedRoute>
          } />

          <Route path='/admin/info' element={
            <ProtectedRoute allowedRole="admin">
              <AdminInfo />
            </ProtectedRoute>
          } />

          {/* This is for Member routing */}
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

          <Route path="/" element={<div className="text-3xl font-bold underline">Welcome to the home page!</div>} />
        </Routes>

      
>>>>>>> Stashed changes
    </Router>
  );
}

export default App;
