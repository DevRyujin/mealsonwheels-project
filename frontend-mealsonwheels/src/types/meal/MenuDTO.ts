// src/types/meal/MenuDTO.ts

import type { MealDTO } from "./MealDTO";

export interface MenuDTO {
  id: number;
  menuName: string;
  menuType: string;
  meals: MealDTO[];
  partnerId: number;
}
