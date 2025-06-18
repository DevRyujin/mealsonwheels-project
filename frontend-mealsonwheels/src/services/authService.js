import api from '../config/api';

export const authService = {
  login: async (credentials) => {
    try {
      const response = await api.post('/api/auth/login', credentials);
      const { token, userType } = response.data;
      
      // Store token and user info
      localStorage.setItem('token', token);
      localStorage.setItem('userType', userType);
      
      return response.data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },

  register: async (userData) => {
    try {
      console.log('Sending registration data:', userData);
      const response = await api.post('/api/auth/register', userData);
      console.log('Registration response:', response.data);
      return response.data;
    } catch (error) {
      console.error('Registration error:', error);
      
      // Handle 401 specifically for registration (shouldn't happen, but just in case)
      if (error.tokenCleared) {
        console.log('Token was cleared due to 401 error');
      }
      
      throw error;
    }
  },

  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userType');
    // Don't automatically redirect, return a flag instead
    return { redirectToLogin: true };
  },

  getCurrentUser: () => {
    const token = localStorage.getItem('token');
    const userType = localStorage.getItem('userType');
    return token ? { token, userType } : null;
  },

  isAuthenticated: () => {
    return !!localStorage.getItem('token');
  },

  // New method to handle manual logout with redirect
  logoutAndRedirect: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userType');
    window.location.href = '/login';
  }
};
