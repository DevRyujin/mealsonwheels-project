import { useState } from 'react'
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
import TestConnection from './pages/TestConnection'; // Add this import

// import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <Router>
      <Header />
      <Navbar />

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
    </Router>
  );
}

export default App;
