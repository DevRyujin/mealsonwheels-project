// src/types/management/AdminStatisticDTO.ts

export interface AdminStatisticDTO {
  totalMembers: number;
  totalPartners: number;
  totalVolunteers: number;
  totalRiders: number;
  totalDonors: number;
  mealsServed: number;
  ordersDelivered: number;
  totalDonationsReceived: string; // From BigDecimal
}
