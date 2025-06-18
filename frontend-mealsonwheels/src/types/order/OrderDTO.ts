// src/types/order/OrderDTO.ts

import type { OrderMealDTO } from "./OrderMealDTO";

export interface OrderDTO {
  id: number;
  totalAmount: number;
  orderType: string;
  status: string;
  createdAt: string;

  memberId: number;
  memberUsername: string;

  partnerId: number;
  partnerName: string;

  riderId: number;

  orderMeals: OrderMealDTO[];
}
