// src/types/order/OrderMealDTO.ts

export interface OrderMealDTO {
  mealId: number;
  mealName: string;
  mealPhoto: Uint8Array | null; // Can also be base64 string depending on frontend handling
  mealDesc: string;
  mealType: string;
  mealDietary: string;
  quantity: number;
  mealCreatedDate: string; // ISO string
}
