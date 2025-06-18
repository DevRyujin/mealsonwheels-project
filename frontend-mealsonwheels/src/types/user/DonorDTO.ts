// src/types/user/DonorDTO.ts

import type { UserDTO } from "./UserDTO";

export interface DonorDTO extends UserDTO {
  donorType: string;
  totalDonatedAmount: number; // BigDecimal mapped as number
}
