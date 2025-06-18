import apiClient from '../../axios';

export const riderApi = {
  getPendingOrders: () => apiClient.get('/rider/orders/pending'),
  getDeliveredOrders: () => apiClient.get('/rider/orders/delivered'),
  getOrderDetails: (id: number) => apiClient.get(`/rider/orders/${id}`),
};
