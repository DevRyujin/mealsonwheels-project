import React, { useState } from 'react';
import AddressInput from './AddressInput';
import type { RegisterDTO } from '@/types/user/Index';

import styles from './RegisterForm.module.css';

const initialFormState: RegisterDTO = {
    username: '',
    email: '',
    phoneNumber: '',
    password: '',
    roleName: 'ROLE_MEMBER',
    address: '',
    dietaryRestriction: '',
    availability: '',
    services: '',
    qualificationAndSkills: '',
    driverLicense: '',
    companyName: '',
    companyDescription: '',
    companyAddress: '',
    supportType: '',
    supdescription: '',
    donorType: '',
};

interface RegisterFormProps {
    onRegister: (form: RegisterDTO) => Promise<void>;
    loading?: boolean;
}

const RegisterForm: React.FC<RegisterFormProps> = ({ onRegister, loading }) => {
    const [form, setForm] = useState<RegisterDTO>(initialFormState);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setForm((prev) => ({
            ...prev,
            [name]: name === 'donationAmount' ? Number(value) : value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (onRegister) {
            const filteredEntries = Object.entries(form).filter(([_, val]) => val !== '');
            const filteredForm = Object.fromEntries(filteredEntries) as Partial<RegisterDTO>;

            const requiredFields = ['username', 'email', 'phoneNumber', 'password', 'roleName'] as const;
            for (const field of requiredFields) {
                if (!filteredForm[field]) {
                    alert(`Missing required field: ${field}`);
                    return;
                }
            }

            await onRegister(filteredForm as RegisterDTO);
        }
    };

    const isApplyRole = ['ROLE_CAREGIVER', 'ROLE_VOLUNTEER', 'ROLE_RIDER', 'ROLE_PARTNER'].includes(form.roleName);
    return (
            <form onSubmit={handleSubmit} className={styles.registerForm}>
                <h2>{isApplyRole ? 'Apply' : 'Register'}</h2>

                <select name="roleName" value={form.roleName} onChange={handleChange} required>
                    <option value="ROLE_MEMBER">Member</option>
                    <option value="ROLE_VOLUNTEER">Volunteer</option>
                    <option value="ROLE_CAREGIVER">Caregiver</option>
                    <option value="ROLE_RIDER">Rider</option>
                    <option value="ROLE_PARTNER">Partner</option>
                    <option value="ROLE_SUPPORTER">Supporter</option>
                    <option value="ROLE_DONOR">Donor</option>
                </select>

                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={form.username}
                    onChange={handleChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={form.email}
                    onChange={handleChange}
                    required
                />
                <input
                    type="text"
                    name="phoneNumber"
                    placeholder="Phone Number"
                    value={form.phoneNumber}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={handleChange}
                    required
                />

            {form.roleName === 'ROLE_MEMBER' && (
                <>
                    <AddressInput onSelectAddress={(address) => setForm((prev) => ({ ...prev, address }))} />
                    <input
                        type="text"
                        name="dietaryRestriction"
                        placeholder="Dietary Restrictions"
                        value={form.dietaryRestriction}
                        onChange={handleChange}
                    />
                </>
            )}

            {form.roleName === 'ROLE_VOLUNTEER' && (
                <>
                    <input
                        type="text"
                        name="availability"
                        placeholder="Availability"
                        value={form.availability}
                        onChange={handleChange}
                    />
                    <input
                        type="text"
                        name="services"
                        placeholder="Services (e.g., Delivery, Packing)"
                        value={form.services}
                        onChange={handleChange}
                    />
                </>
            )}

            {form.roleName === 'ROLE_CAREGIVER' && (
                <input
                    type="text"
                    name="qualificationAndSkills"
                    placeholder="Qualification and Skills"
                    value={form.qualificationAndSkills}
                    onChange={handleChange}
                />
            )}

            {form.roleName === 'ROLE_RIDER' && (
                <input
                    type="text"
                    name="driverLicense"
                    placeholder="Driver License Number"
                    value={form.driverLicense}
                    onChange={handleChange}
                />
            )}

            {form.roleName === 'ROLE_PARTNER' && (
                <>
                    <input
                        type="text"
                        name="companyName"
                        placeholder="Company Name"
                        value={form.companyName}
                        onChange={handleChange}
                    />
                    <input
                        type="text"
                        name="companyDescription"
                        placeholder="Company Description"
                        value={form.companyDescription}
                        onChange={handleChange}
                    />
                    <AddressInput
                        onSelectAddress={(companyAddress) =>
                            setForm((prev) => ({ ...prev, companyAddress }))
                        }
                    />
                </>
            )}

            {form.roleName === 'ROLE_SUPPORTER' && (
                <>
                    <input
                        type="text"
                        name="supportType"
                        placeholder="Support Type"
                        value={form.supportType}
                        onChange={handleChange}
                    />
                    <input
                        type="text"
                        name="supdescription"
                        placeholder="Support Description"
                        value={form.supdescription}
                        onChange={handleChange}
                    />
                </>
            )}

            {form.roleName === 'ROLE_DONOR' && (
                <>
                    <select
                        name="donorType"
                        value={form.donorType}
                        onChange={handleChange}
                        required
                    >
                        <option value="" disabled>
                            Select Donor Type
                        </option>
                        <option value="Individual">Individual</option>
                        <option value="Corporate">Corporate</option>
                        <option value="Foundation">Foundation</option>
                    </select>
                </>
            )}
            <button type="submit" disabled={loading}>
                {loading ? 'Registering...' : 'Register'}
            </button>
        </form>
    );
};

export default RegisterForm;
