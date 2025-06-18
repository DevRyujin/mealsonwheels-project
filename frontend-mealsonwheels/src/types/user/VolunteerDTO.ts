// src/types/user/VolunteerDTO.ts

import type { UserDTO } from "./UserDTO";

export interface VolunteerDTO extends UserDTO {
  availability: string;
  services: string;
}
