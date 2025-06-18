// src/types/management/IngredientDTO.ts

export interface IngredientDTO {
  id: number;
  name: string;
  expirationDate: string; // ISO date string (e.g., "2025-06-06")
  quantity: number;
  unit: string;
  storageConditions: string;
}
