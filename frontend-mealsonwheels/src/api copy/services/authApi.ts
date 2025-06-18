// src/api/services/authApi.ts

import api from '../axios';
import type { LoginDTO, RegisterDTO, UserDTO } from '@/types/user/Index';

export const login = (data: LoginDTO) =>
  api.post<{ token: string }>('/auth/login', data);

// Role-based registration
export const registerAdmin = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/admin', data);

export const registerMember = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/member', data);

export const registerVolunteer = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/volunteer', data);

export const registerRider = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/rider', data);

export const registerCaregiver = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/caregiver', data);

export const registerPartner = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/partner', data);

export const registerSupporter = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/supporter', data);

export const registerDonor = (data: RegisterDTO) =>
  api.post<UserDTO>('/auth/register/donor', data);
