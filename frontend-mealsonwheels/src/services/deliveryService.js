import api from '../api/axiosInstance';

export const deliveryService = {
  
  getActiveDeliveries: async () => {
    const response = await api.get('/rider/orders/pending');
    return response.data;
  },

  getOutForDelivery: async () => {
    const response = await api.get('/rider/orders/pending'); // same endpoint
    return response.data.filter((order) => order.status === "IN_PROGRESS");
  },

  getCompletedDeliveries: async () => {
    const response = await api.get('/rider/orders/delivered');
    return response.data;
  },

  startDelivery: async (deliveryId) => {
    const response = await api.post(`/rider/orders/${deliveryId}/start`);
    return response.data;
  },

  completeDelivery: async (deliveryId) => {
    const response = await api.post(`/rider/orders/${deliveryId}/complete`);
    return response.data;
  },

  getRiderProfile: async () => {
  const response = await api.get('/rider/me');
  return response.data;
},

acceptDelivery: async (deliveryId) => {
  const response = await api.post(`/rider/orders/${deliveryId}/accept`);
  return response.data;
},


};
