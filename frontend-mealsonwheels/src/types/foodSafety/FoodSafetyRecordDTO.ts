// src/types/management/FoodSafetyRecordDTO.ts

export interface FoodSafetyRecordDTO {
  id: number;
  inspectionDate: string; // ISO date-time string (e.g., "2025-06-06T14:00:00")
  safetyChecklistCompleted: boolean;
  inspectionNotes?: string;
  ingredientId: number;
}
