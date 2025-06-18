// src/types/order/OrderRequestDTO.ts

import type { OrderMealDTO } from "./OrderMealDTO";

export interface OrderRequestDTO {
  memberId: number;
  partnerId: number;
  totalAmount: number;
  riderId: number;

  mealIds: number[];
  meals: OrderMealDTO[];
}
