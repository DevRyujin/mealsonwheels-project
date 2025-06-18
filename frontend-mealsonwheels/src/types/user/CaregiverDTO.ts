// src/types/user/CaregiverDTO.ts

import type { UserDTO } from "./UserDTO";

export interface CaregiverDTO extends UserDTO {
  qualificationAndSkills: string;
  memberIds: number[]; // could be MemberDTO[] if full member details are used
}
