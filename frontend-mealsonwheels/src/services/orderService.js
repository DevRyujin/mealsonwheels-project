import axiosInstance from '../api/axiosInstance';

export const orderService = {
  placeOrder: async (orderPayload) => {
    const response = await axiosInstance.post('/orders/place', orderPayload);
    return response.data;
  },

  placeOrderForCaregiver: async (orderPayload) => {
    const response = await axiosInstance.post('/orders/caregiver/place/', orderPayload);
    return response.data;
  }
};
