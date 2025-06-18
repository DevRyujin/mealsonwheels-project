// src/hooks/useLogin.ts
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '@/api/services/authApi';
import type { LoginDTO } from '@/types/user/LoginDTO';
import { useAuth } from '@/context/AuthContext';

function parseJwt(token: string) {
  try {
    const base64Payload = token.split('.')[1];
    const payload = atob(base64Payload);
    return JSON.parse(payload);
  } catch {
    return null;
  }
}

export const useLogin = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const { setAuthToken } = useAuth();

  const roleToDashboardPath: Record<string, string> = {
    ADMIN: '/dashboard/admin',
    PARTNER: '/dashboard/partner',
    CAREGIVER: '/dashboard/caregiver',
    SUPPORTER: '/dashboard/supporter',
    VOLUNTEER: '/dashboard/volunteer',
    DONOR: '/dashboard/donor',
    MEMBER: '/dashboard/member',
    RIDER: '/dashboard/rider',
  };

  const handleLogin = async (credentials: LoginDTO) => {
    setLoading(true);
    setError(null);
    try {
      const response = await login(credentials);
      const token = response.data.token;

      setAuthToken(token); // store in context/localStorage

      const decoded = parseJwt(token);
      const userRole = decoded?.role || decoded?.roles?.[0];
      const isApproved = decoded?.approved;

      if (!isApproved) {
        navigate('/approval-pending');
        return;
      }

      const targetPath = roleToDashboardPath[userRole?.toUpperCase()] || '/dashboard';
      navigate(targetPath);
    } catch (err: any) {
      const msg = err.response?.data?.message || 'Login failed.';
      if (msg.toLowerCase().includes('approval')) {
        navigate('/approval-pending');
        return;
      }
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return { handleLogin, loading, error };
};
