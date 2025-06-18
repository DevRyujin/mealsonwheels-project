// src/types/user/SupporterDTO.ts

import type { UserDTO } from "./UserDTO";

export interface SupporterDTO extends UserDTO {
  supportType: string;
  supDescription: string;
}
