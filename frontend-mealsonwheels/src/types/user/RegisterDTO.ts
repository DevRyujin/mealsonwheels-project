// src/types/user/RegisterDTO.ts

export interface RegisterDTO {
  username: string;
  phoneNumber: string;
  email: string;
  password: string;
  roleName: string;

  // Member-specific
  address?: string;
  dietaryRestriction?: string;

  // Volunteer-specific
  availability?: string;
  services?: string;

  // Rider-specific
  driverLicense?: string;

  // Caregiver-specific
  qualificationAndSkills?: string;

  // Partner-specific
  companyName?: string;
  companyDescription?: string;
  companyAddress?: string;

  // Supporter-specific
  supportType?: string;
  supdescription?: string;

  // Donor-specific
  donorType?: string;
  donationAmount?: number;
}

export type RoleName =
  | 'ROLE_ADMIN'
  | 'ROLE_MEMBER'
  | 'ROLE_VOLUNTEER'
  | 'ROLE_CAREGIVER'
  | 'ROLE_RIDER'
  | 'ROLE_PARTNER'
  | 'ROLE_SUPPORTER'
  | 'ROLE_DONOR';