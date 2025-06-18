// src/hooks/auth/useRegister.ts
import { useState } from 'react';
import * as authApi from '@/api/services/authApi';
import type { RegisterDTO } from '@/types/user/RegisterDTO';
import { useLogin } from '@/hooks/useLogin';

export const useRegister = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { handleLogin } = useLogin();

  const handleRegister = async (data: RegisterDTO) => {
    setLoading(true);
    setError(null);
    try {
      const registerFunction = {
        ADMIN: authApi.registerAdmin,
        MEMBER: authApi.registerMember,
        VOLUNTEER: authApi.registerVolunteer,
        RIDER: authApi.registerRider,
        CAREGIVER: authApi.registerCaregiver,
        PARTNER: authApi.registerPartner,
        SUPPORTER: authApi.registerSupporter,
        DONOR: authApi.registerDonor,
      }[data.roleName.toUpperCase()];

      if (!registerFunction) throw new Error('Invalid role');

      await registerFunction(data);

      // Auto-login after registration
      await handleLogin({ email: data.email, password: data.password });
    } catch (err: any) {
      setError(err.response?.data?.message || 'Registration failed.');
    } finally {
      setLoading(false);
    }
  };

  return { handleRegister, loading, error };
};
