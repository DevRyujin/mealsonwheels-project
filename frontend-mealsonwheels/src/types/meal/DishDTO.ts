// src/types/meal/DishDTO.ts

export interface DishDTO {
  id: number;
  dishName: string;
  dishPhoto: Uint8Array | null; // Representing byte[] in JS
  dishDesc: string;
  partnerId: number;
  mealId: number;
  menuId: number;
  dishType: string;
  dishDietary: string;
  dishCreatedDate: string; // ISO string (LocalDateTime)
}
