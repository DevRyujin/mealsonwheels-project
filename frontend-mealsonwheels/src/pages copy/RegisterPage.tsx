import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import RegisterForm from '../components/RegisterForm';
import axios from 'axios';
import {
    registerAdmin,
    registerMember,
    registerVolunteer,
    registerCaregiver,
    registerRider,
    registerPartner,
    registerSupporter,
    registerDonor,
} from '../api/services/authApi';
import type { RegisterDTO, RoleName, UserDTO } from '@/types/user/Index';
import type { AxiosResponse } from 'axios';

const roleToRegisterFn: Record<
    RoleName,
    (data: RegisterDTO) => Promise<AxiosResponse<UserDTO>>
> = {
    ROLE_ADMIN: registerAdmin,
    ROLE_MEMBER: registerMember,
    ROLE_VOLUNTEER: registerVolunteer,
    ROLE_CAREGIVER: registerCaregiver,
    ROLE_RIDER: registerRider,
    ROLE_PARTNER: registerPartner,
    ROLE_SUPPORTER: registerSupporter,
    ROLE_DONOR: registerDonor,
};

const RegisterPage: React.FC = () => {
    const navigate = useNavigate();
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [formKey, setFormKey] = useState(0);

    const handleRegister = async (formData: RegisterDTO) => {
        try {
            setError('');
            setLoading(true);

            const registerFn = roleToRegisterFn[formData.roleName as RoleName];
            if (!registerFn) throw new Error('Invalid role for registration');

            await registerFn(formData);

            // ✅ Just redirect to login after successful registration
            setFormKey((prev) => prev + 1);
            navigate('/register/success');
        } catch (err) {
            if (axios.isAxiosError(err)) {
                setError(err.response?.data?.message || err.message);
            } else {
                setError('Registration failed.');
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="register-page">
            <h1>Create an Account</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <RegisterForm key={formKey} onRegister={handleRegister} loading={loading} />
        </div>
    );
};

export default RegisterPage;
