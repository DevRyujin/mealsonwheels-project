// src/types/user/RiderDTO.ts

import type { UserDTO } from "./UserDTO";

export interface RiderDTO extends UserDTO {
  driverLicenseNumber: string;
  partnerId: number;
}
