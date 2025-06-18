import { useState, useEffect } from 'react';
import api from '../config/api';

const TestConnection = () => {
  const [results, setResults] = useState({
    basicConnection: { status: 'pending', message: '', data: null },
    healthCheck: { status: 'pending', message: '', data: null },
    publicEndpoint: { status: 'pending', message: '', data: null },
    authEndpoint: { status: 'pending', message: '', data: null },
    protectedEndpoint: { status: 'pending', message: '', data: null }
  });

  const updateResult = (test, status, message, data = null) => {
    setResults(prev => ({
      ...prev,
      [test]: { status, message, data }
    }));
  };

  // Test 1: Basic connection to backend root
  const testBasicConnection = async () => {
    try {
      updateResult('basicConnection', 'testing', 'Testing basic connection...');
      const response = await fetch('http://localhost:8080/');
      if (response.ok) {
        const data = await response.json();
        updateResult('basicConnection', 'success', 'Backend server is running!', data);
      } else {
        updateResult('basicConnection', 'warning', `Server responded with status: ${response.status}`);
      }
    } catch (error) {
      updateResult('basicConnection', 'error', `Connection failed: ${error.message}`);
    }
  };

  // Test 2: Health check endpoint
  const testHealthCheck = async () => {
    try {
      updateResult('healthCheck', 'testing', 'Testing health endpoint...');
      const response = await api.get('/health');
      updateResult('healthCheck', 'success', 'Health endpoint working!', response.data);
    } catch (error) {
      if (error.response) {
        updateResult('healthCheck', 'warning', 
          `Health endpoint returned: ${error.response.status}`,
          error.response.data
        );
      } else {
        updateResult('healthCheck', 'error', `Health check error: ${error.message}`);
      }
    }
  };

  // Test 3: Test auth test endpoint
  const testPublicEndpoint = async () => {
    try {
      updateResult('publicEndpoint', 'testing', 'Testing public auth endpoint...');
      const response = await api.get('/api/auth/test');
      updateResult('publicEndpoint', 'success', 'Public endpoint accessible!', response.data);
    } catch (error) {
      if (error.response) {
        updateResult('publicEndpoint', 'warning', 
          `Public endpoint returned: ${error.response.status} - ${error.response.data?.message || 'Unknown error'}`,
          error.response.data
        );
      } else if (error.request) {
        updateResult('publicEndpoint', 'error', 'No response from server (CORS or network issue)');
      } else {
        updateResult('publicEndpoint', 'error', `Request error: ${error.message}`);
      }
    }
  };

  // Test 4: Test registration (don't actually register)
  const testAuthEndpoints = async () => {
    try {
      updateResult('authEndpoint', 'testing', 'Testing registration endpoint structure...');
      
      // Test with minimal data first to see what the endpoint expects
      const testData = {
        username: 'testuser_' + Date.now(),
        email: 'test' + Date.now() + '@example.com',
        password: 'TestPassword123!',
        userType: 'member'
      };
      
      console.log('Testing registration with:', testData);
      const response = await api.post('/api/auth/register', testData);
      updateResult('authEndpoint', 'success', 'Registration endpoint working!', response.data);
    } catch (error) {
      console.error('Registration test error:', error);
      if (error.response) {
        updateResult('authEndpoint', 'warning', 
          `Registration endpoint returned: ${error.response.status} - ${error.response.data?.message || 'Server error'}`,
          {
            status: error.response.status,
            data: error.response.data,
            headers: error.response.headers
          }
        );
      } else {
        updateResult('authEndpoint', 'error', `Registration test error: ${error.message}`);
      }
    }
  };

  // Test 5: Test protected endpoint
  const testProtectedEndpoint = async () => {
    try {
      updateResult('protectedEndpoint', 'testing', 'Testing protected endpoint...');
      const response = await api.get('/api/admin/test');
      updateResult('protectedEndpoint', 'success', 'Protected endpoint accessible!', response.data);
    } catch (error) {
      if (error.response?.status === 401) {
        updateResult('protectedEndpoint', 'info', 'Protected endpoint working (requires authentication)');
      } else if (error.response?.status === 403) {
        updateResult('protectedEndpoint', 'info', 'Protected endpoint working (requires admin role)');
      } else if (error.response) {
        updateResult('protectedEndpoint', 'warning', 
          `Protected endpoint returned: ${error.response.status}`,
          error.response.data
        );
      } else {
        updateResult('protectedEndpoint', 'error', `Protected endpoint error: ${error.message}`);
      }
    }
  };

  // Run all tests
  const runAllTests = () => {
    testBasicConnection();
    testHealthCheck();
    testPublicEndpoint();
    testAuthEndpoints();
    testProtectedEndpoint();
  };

  useEffect(() => {
    runAllTests();
  }, []);

  const getStatusColor = (status) => {
    switch (status) {
      case 'success': return 'text-green-600 bg-green-100';
      case 'warning': return 'text-yellow-600 bg-yellow-100';
      case 'error': return 'text-red-600 bg-red-100';
      case 'info': return 'text-blue-600 bg-blue-100';
      case 'testing': return 'text-gray-600 bg-gray-100';
      default: return 'text-gray-500 bg-gray-50';
    }
  };

  const getStatusIcon = (status) => {
    switch (status) {
      case 'success': return '✅';
      case 'warning': return '⚠️';
      case 'error': return '❌';
      case 'info': return 'ℹ️';
      case 'testing': return '🔄';
      default: return '⏳';
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-4xl mx-auto px-4">
        <div className="bg-white rounded-lg shadow-lg p-6">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-3xl font-bold text-gray-900">Backend Connection Test</h1>
            <button
              onClick={runAllTests}
              className="px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
            >
              Run Tests Again
            </button>
          </div>

          <div className="space-y-6">
            {Object.entries(results).map(([testName, result]) => (
              <div key={testName} className="border rounded-lg p-4">
                <div className="flex items-center justify-between mb-2">
                  <h3 className="text-lg font-semibold capitalize">
                    {testName.replace(/([A-Z])/g, ' $1').trim()}
                  </h3>
                  <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(result.status)}`}>
                    {getStatusIcon(result.status)} {result.status.toUpperCase()}
                  </span>
                </div>
                
                <p className="text-gray-700 mb-2">{result.message}</p>
                
                {result.data && (
                  <details className="mt-2">
                    <summary className="cursor-pointer text-blue-600 hover:text-blue-800">
                      Show Response Data
                    </summary>
                    <pre className="mt-2 p-3 bg-gray-100 rounded text-sm overflow-x-auto">
                      {JSON.stringify(result.data, null, 2)}
                    </pre>
                  </details>
                )}
              </div>
            ))}
          </div>

          {/* Connection Info */}
          <div className="mt-8 p-4 bg-blue-50 rounded-lg">
            <h3 className="text-lg font-semibold text-blue-900 mb-2">Connection Information</h3>
            <div className="text-sm text-blue-800 space-y-1">
              <p><strong>Frontend URL:</strong> {window.location.origin}</p>
              <p><strong>Backend URL:</strong> http://localhost:8080</p>
              <p><strong>Current User:</strong> {localStorage.getItem('userType') || 'Not logged in'}</p>
              <p><strong>Token Present:</strong> {localStorage.getItem('token') ? 'Yes' : 'No'}</p>
            </div>
          </div>

          {/* Quick Actions */}
          <div className="mt-6 flex space-x-4">
            <button
              onClick={() => window.location.href = '/login'}
              className="px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700"
            >
              Go to Login
            </button>
            <button
              onClick={() => window.location.href = '/register'}
              className="px-4 py-2 bg-purple-600 text-white rounded-md hover:bg-purple-700"
            >
              Go to Register
            </button>
            <button
              onClick={() => {
                localStorage.clear();
                alert('Local storage cleared!');
                runAllTests();
              }}
              className="px-4 py-2 bg-red-600 text-white rounded-md hover:bg-red-700"
            >
              Clear Storage & Retest
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TestConnection;
