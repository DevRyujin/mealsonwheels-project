// src/types/user/PartnerDTO.ts

import type { UserDTO } from "./UserDTO";
import type { DishDTO } from "../meal/DishDTO";
import type { MealDTO } from "../meal/MealDTO";
import type { MenuDTO } from "../meal/MenuDTO";

export interface PartnerDTO extends UserDTO {
  companyName: string;
  companyDescription: string;
  companyAddress: string;
  companyLocationLat: number;
  companyLocationLong: number;
  dishes: DishDTO[];
  providedMeals: MealDTO[];
  menus: MenuDTO[];
}
