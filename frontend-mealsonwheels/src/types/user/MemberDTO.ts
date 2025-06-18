// src/types/user/MemberDTO.ts

import type { UserDTO } from "./UserDTO";

export interface MemberDTO extends UserDTO {
  dietaryRestriction: string;
  address: string;
  memberLocationLat: number;
  memberLocationLong: number;
  caregiverId: number;
}
