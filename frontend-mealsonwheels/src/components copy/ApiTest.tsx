import React, { useState, useEffect } from 'react';
import { api } from '../api/services/api';

interface ApiResponse {
  status?: string;
  message?: string;
  success?: boolean;
  data?: string;
  timestamp?: number;
}

const ApiTest: React.FC = () => {
  const [healthData, setHealthData] = useState<ApiResponse | null>(null);
  const [testData, setTestData] = useState<ApiResponse | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [connectionStatus, setConnectionStatus] = useState<'unknown' | 'connected' | 'disconnected'>('unknown');

  const testConnection = async () => {
    setLoading(true);
    setError(null);
    setConnectionStatus('unknown');
    
    try {
      // Test health endpoint
      const healthResponse = await api.health();
      setHealthData(healthResponse.data);
      
      // Test data endpoint
      const testResponse = await api.test();
      setTestData(testResponse.data);
      
      setConnectionStatus('connected');
      
    } catch (err: any) {
      setConnectionStatus('disconnected');
      
      if (err.code === 'ERR_NETWORK') {
        setError('Network Error: Cannot connect to backend. Make sure the Spring Boot server is running on port 8080.');
      } else if (err.response?.status === 404) {
        setError('API endpoints not found. Check if the controllers are properly configured.');
      } else if (err.response?.status === 403) {
        setError('CORS Error: Backend is rejecting requests from this origin.');
      } else {
        setError(`Connection failed: ${err.message || 'Unknown error occurred'}`);
      }
      
      console.error('Connection error:', err);
    } finally {
      setLoading(false);
    }
  };

  const getStatusColor = () => {
    switch (connectionStatus) {
      case 'connected': return '#4CAF50';
      case 'disconnected': return '#f44336';
      default: return '#ff9800';
    }
  };

  const getStatusText = () => {
    switch (connectionStatus) {
      case 'connected': return '✅ Connected';
      case 'disconnected': return '❌ Disconnected';
      default: return '⏳ Unknown';
    }
  };

  useEffect(() => {
    testConnection();
  }, []);

  const containerStyle: React.CSSProperties = {
    padding: '20px',
    fontFamily: 'Arial, sans-serif',
    maxWidth: '800px',
    margin: '0 auto',
    backgroundColor: '#f5f5f5',
    borderRadius: '8px',
    boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
  };

  const headerStyle: React.CSSProperties = {
    color: '#333',
    textAlign: 'center',
    marginBottom: '20px',
    borderBottom: '2px solid #ddd',
    paddingBottom: '10px'
  };

  const statusStyle: React.CSSProperties = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '10px',
    margin: '20px 0',
    padding: '10px',
    backgroundColor: 'white',
    borderRadius: '5px',
    border: `2px solid ${getStatusColor()}`
  };

  const buttonStyle: React.CSSProperties = {
    backgroundColor: loading ? '#ccc' : '#007bff',
    color: 'white',
    border: 'none',
    padding: '12px 24px',
    borderRadius: '5px',
    cursor: loading ? 'not-allowed' : 'pointer',
    fontSize: '16px',
    fontWeight: 'bold',
    transition: 'background-color 0.3s',
    display: 'block',
    margin: '0 auto'
  };

  const errorStyle: React.CSSProperties = {
    color: '#d32f2f',
    backgroundColor: '#ffebee',
    border: '1px solid #f8bbd9',
    borderRadius: '5px',
    padding: '15px',
    margin: '15px 0',
    fontWeight: 'bold'
  };

  const responseBoxStyle: React.CSSProperties = {
    margin: '20px 0',
    padding: '15px',
    border: '1px solid #ddd',
    borderRadius: '5px',
    backgroundColor: 'white',
    boxShadow: '0 1px 3px rgba(0,0,0,0.1)'
  };

  const preStyle: React.CSSProperties = {
    color: 'black',
    backgroundColor: '#f8f9fa',
    border: '1px solid #e9ecef',
    borderRadius: '4px',
    padding: '10px',
    overflow: 'auto',
    fontSize: '14px',
    lineHeight: '1.4'
  };

  return (
    <div style={containerStyle}>
      <h2 style={headerStyle}>MelsOnWels Frontend - Backend Connection Test</h2>
      
      <div style={statusStyle}>
        <span style={{ fontSize: '18px', fontWeight: 'bold', color: getStatusColor() }}>
          Status: {getStatusText()}
        </span>
      </div>
      
      <button 
        onClick={testConnection} 
        disabled={loading}
        style={buttonStyle}
        onMouseOver={(e) => {
          if (!loading) {
            (e.target as HTMLButtonElement).style.backgroundColor = '#0056b3';
          }
        }}
        onMouseOut={(e) => {
          if (!loading) {
            (e.target as HTMLButtonElement).style.backgroundColor = '#007bff';
          }
        }}
      >
        {loading ? '🔄 Testing Connection...' : '🔗 Test Backend Connection'}
      </button>
      
      {error && (
        <div style={errorStyle}>
          <strong>❌ Error:</strong> {error}
          <div style={{ marginTop: '10px', fontSize: '14px', fontWeight: 'normal' }}>
            <strong>Troubleshooting:</strong>
            <ul style={{ marginTop: '5px', paddingLeft: '20px' }}>
              <li>Ensure Spring Boot server is running on port 8080</li>
              <li>Check if CORS is properly configured</li>
              <li>Verify API endpoints exist in your controllers</li>
              <li>Check browser console for detailed error messages</li>
            </ul>
          </div>
        </div>
      )}
      
      {healthData && (
        <div style={responseBoxStyle}>
          <h3 style={{ color: '#28a745', marginTop: '0' }}>✅ Health Check Response:</h3>
          <pre style={preStyle}>{JSON.stringify(healthData, null, 2)}</pre>
        </div>
      )}
      
      {testData && (
        <div style={responseBoxStyle}>
          <h3 style={{ color: '#28a745', marginTop: '0' }}>✅ Test Data Response:</h3>
          <pre style={preStyle}>{JSON.stringify(testData, null, 2)}</pre>
          {testData.timestamp && (
            <p style={{ margin: '10px 0 0 0', fontSize: '14px', color: '#666' }}>
              <strong>Response Time:</strong> {new Date(testData.timestamp).toLocaleString()}
            </p>
          )}
        </div>
      )}
      
      {connectionStatus === 'connected' && (
        <div style={{
          backgroundColor: '#d4edda',
          border: '1px solid #c3e6cb',
          borderRadius: '5px',
          padding: '15px',
          margin: '20px 0',
          color: '#155724'
        }}>
          <strong>🎉 Success!</strong> Your React frontend is successfully connected to the Spring Boot backend.
          <br />
          <small>Backend URL: http://localhost:8080/api</small>
        </div>
      )}
      
      <div style={{
        marginTop: '30px',
        padding: '15px',
        backgroundColor: '#e3f2fd',
        border: '1px solid #bbdefb',
        borderRadius: '5px',
        fontSize: '14px'
      }}>
        <h4 style={{ margin: '0 0 10px 0', color: '#1976d2' }}>ℹ️ Connection Info:</h4>
        <ul style={{ margin: '0', paddingLeft: '20px' }}>
          <li><strong>Frontend:</strong> React + Vite + TypeScript (Port 5173)</li>
          <li><strong>Backend:</strong> Spring Boot + MySQL (Port 8080)</li>
          <li><strong>API Base URL:</strong> http://localhost:8080/api</li>
          <li><strong>CORS:</strong> Configured for localhost:5173</li>
        </ul>
      </div>
    </div>
  );
};

export default ApiTest;
