import apiClient from '../axios';
import type { OrderDTO, OrderRequestDTO } from '@/types/order/Index';

export const orderApi = {
  placeOrder: (data: OrderRequestDTO): Promise<{ data: string }> =>
    apiClient.post('/orders/place', data),

  assignRider: (orderId: number, riderId: number): Promise<{ data: string }> =>
    apiClient.post(`/orders/${orderId}/assign-rider/${riderId}`),

  getAllOrders: (): Promise<{ data: OrderDTO[] }> => apiClient.get('/orders'),

  getOrderById: (orderId: number): Promise<{ data: OrderDTO }> =>
    apiClient.get(`/orders/${orderId}`),
};
