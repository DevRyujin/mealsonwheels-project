// src/pages/LoginPage.tsx

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '@/api/services/authApi';
import { useAuth } from '@/context/AuthContext';
import { jwtDecode } from 'jwt-decode';
import type { LoginDTO } from '@/types/user/Index';

interface DecodedToken {
  sub: string;
  approved?: boolean;
  role?: string;
  user?: {
    approved?: boolean;
    role?: string;
    status?: string;
    [key: string]: any;
  };
  [key: string]: any;
}

const LoginPage: React.FC = () => {
  const navigate = useNavigate();
  const { setAuthToken } = useAuth();

  const [formData, setFormData] = useState<LoginDTO>({ email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await login(formData);
      const { token } = response.data;
      setAuthToken(token);

      const decoded: DecodedToken = jwtDecode(token);
      console.log('Decoded token:', decoded);

      const isApproved =
        decoded?.approved === true ||
        decoded?.user?.approved === true ||
        decoded?.user?.status === 'APPROVED';

      console.log('isApproved:', isApproved);

      if (!isApproved) {
        setTimeout(() => navigate('/approval-pending'), 0);
      } else {
        setTimeout(() => navigate('/'), 0);
      }
    } catch (err: any) {
      const apiError = err.response?.data;
      const errorMessage =
        typeof apiError === 'string'
          ? apiError
          : apiError?.message || 'Login failed. Please try again.';

      // Handle backend denial with specific message
      if (
        err.response?.status === 403 &&
        apiError?.message === 'Account is pending admin approval.'
      ) {
        setTimeout(() => navigate('/approval-pending'), 0);
        return;
      }

      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100 px-4">
      <div className="bg-white p-8 rounded-xl shadow-md w-full max-w-md">
        <h2 className="text-2xl font-semibold text-center mb-6">Login to Your Account</h2>

        {error && (
          <div className="mb-4 text-red-600 text-sm text-center bg-red-100 p-2 rounded">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700">Email</label>
            <input
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              required
              autoComplete="email"
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Password</label>
            <input
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              required
              autoComplete="current-password"
              className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 transition duration-200"
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <p className="text-sm text-center mt-4 text-gray-600">
          Don't have an account?{' '}
          <span
            onClick={() => navigate('/register')}
            className="text-blue-600 hover:underline cursor-pointer"
          >
            Register
          </span>
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
