// src/hooks/AuthContext.tsx
import { createContext, useContext, useState, useEffect } from 'react';
import type { ReactNode } from 'react';
import { jwtDecode } from 'jwt-decode';

interface JwtPayload {
  sub: string;       // username/email
  roles: string | string[];
  userId: number;
  exp: number;
}

interface AuthContextType {
  isAuthenticated: boolean;
  user: string | null;
  role: string | null;
  userId: number | null;
  logout: () => void;
  setAuthToken: (token: string | null) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [user, setUser] = useState<string | null>(null);
  const [role, setRole] = useState<string | null>(null);
  const [userId, setUserId] = useState<number | null>(null);

  const parseToken = (token: string | null) => {
    if (!token) return null;
    try {
      const decoded = jwtDecode<JwtPayload>(token);
      const isExpired = decoded.exp * 1000 < Date.now();
      if (isExpired) return null;
      return decoded;
    } catch {
      return null;
    }
  };

  const updateAuthState = (token: string | null) => {
    if (!token) {
      setIsAuthenticated(false);
      setUser(null);
      setRole(null);
      setUserId(null);
      localStorage.removeItem('authToken');
      return;
    }

    const decoded = parseToken(token);
    if (decoded) {
      setIsAuthenticated(true);
      setUser(decoded.sub);
      // roles can be string or string[], handle both
      if (typeof decoded.roles === 'string') {
        setRole(decoded.roles);
      } else if (Array.isArray(decoded.roles)) {
        setRole(decoded.roles[0] || null);
      } else {
        setRole(null);
      }
      setUserId(decoded.userId);
      localStorage.setItem('authToken', token);
    } else {
      setIsAuthenticated(false);
      setUser(null);
      setRole(null);
      setUserId(null);
      localStorage.removeItem('authToken');
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('authToken');
    updateAuthState(token);
  }, []);

  const logout = () => {
    updateAuthState(null);
  };

  const setAuthToken = (token: string | null) => {
    updateAuthState(token);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, user, role, userId, logout, setAuthToken }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
