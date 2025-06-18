// src/types/meal/MealDTO.ts

export interface MealDTO {
  id: number;
  mealName: string;
  mealPhoto: Uint8Array | null;
  mealDesc: string;
  partnerId: number;
  menuId: number;
  mealType: string;
  mealDietary: string;
  mealCreatedDate: string;
}
