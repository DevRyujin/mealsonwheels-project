import { useState } from 'react';
import './App.css';
import { Header } from './components/Header';
import { Navbar } from './components/Navbar';
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
import MemberDashboard from './pages/MemberDashboard';
import PartnerDashboard from './pages/PartnerDashboard';
import VolunteerDashboard from './pages/VolunteerDashboard';

// Auth and test
import Login from './pages/Login';
import Register from './pages/Register';
import TestConnection from './pages/TestConnection';

import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <Router>
      <Header />
      <Navbar />

      <Routes>
        {/* Admin routes */}
        <Route path="/admin/dashboard" element={
          <ProtectedRoute allowedRole="admin">
            <Dashboard />
          </ProtectedRoute>
        } />
        <Route path="/admin/members" element={
          <ProtectedRoute allowedRole="admin">
            <MembersAndCaregivers />
          </ProtectedRoute>
        } />
        <Route path="/admin/partners" element={
          <ProtectedRoute allowedRole="admin">
            <CompanyPartners />
          </ProtectedRoute>
        } />
        <Route path="/admin/volunteers" element={
          <ProtectedRoute allowedRole="admin">
            <CompanyVolunteers />
          </ProtectedRoute>
        } />
        <Route path="/admin/feedbacks" element={
          <ProtectedRoute allowedRole="admin">
            <MemberFeedbacks />
          </ProtectedRoute>
        } />
        <Route path="/admin/menus" element={
          <ProtectedRoute allowedRole="admin">
            <MenuDescriptions />
          </ProtectedRoute>
        } />
        <Route path="/admin/deliveries" element={
          <ProtectedRoute allowedRole="admin">
            <ManageDeliveries />
          </ProtectedRoute>
        } />
        <Route path="/admin/info" element={
          <ProtectedRoute allowedRole="admin">
            <AdminInfo />
          </ProtectedRoute>
        } />

        {/* Other role routes */}
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

        {/* Auth and testing */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/test" element={<TestConnection />} />

        {/* Home */}
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
    </Router>
  );
}

export default App;
