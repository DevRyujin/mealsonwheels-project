import { useState } from 'react'
import './App.css';
import Header from './components/Header';
import Navbar from './components/Navbar';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Imported Pages
import Admin from './pages/Admin-Dashboard';

// Import more if needed


function App() {

  return (
    <Router>
      <Header />
      <Navbar />

        <Routes>
          <Route path='/admin' element={<Admin />} />

          <Route path="/" element={<div class="text-3xl font-bold underline">Welcome to the home page!</div>} />
        </Routes>

      
    </Router>
      
  );
}

export default App
