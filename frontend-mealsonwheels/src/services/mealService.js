import api from '../api/axiosInstance';

export const mealService = {
  getMeals: async () => {
    const response = await api.get('/partner/meals');
    return response.data;
  },

  deleteMeal: async (id) => {
    const response = await api.delete(`/meals/${id}`);
    return response.data;
  },

  updateMeal: async (id, updatedMeal) => {
    const response = await api.put(`/partner/meals/${id}`, updatedMeal);
    return response.data;
  },

  createMeal: async (mealData) => {
    const response = await api.post('/meals', mealData);
    return response.data;
  },

  // ✅ New function to get meal history for members
  getMealHistory: async () => {
    const response = await api.get('/orders/pending/history');
    return response.data;
  },

  getDeliveredMealHistory: async () => {
    const response = await api.get('/orders/delivered/history');
    return response.data;
  },

  rateMeal: async (orderId, feedbackData) => {
    const response = await api.post(`/orders/rate/${orderId}`, feedbackData);
    return response.data;
  },

};
