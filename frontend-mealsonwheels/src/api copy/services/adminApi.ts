import apiClient from '../axios';  // your axios instance

export const adminApi = {
  // User management
  approveUser: (userId: number) => apiClient.put(`/admin/approve-user/${userId}`),
  rejectUser: (userId: number) => apiClient.put(`/admin/reject-user/${userId}`),
  getUsersByRole: (role: string) => apiClient.get(`/admin/users`, { params: { role } }),

  // Statistics & tasks
  getStatistics: () => apiClient.get('/admin/statistics'),
  assignTask: (userId: number, task: string) => 
    apiClient.post('/admin/assign-task', null, { params: { userId, task } }),

  // Ingredients
  addIngredient: (ingredient: any) => apiClient.post('/admin/ingredients', ingredient),
  updateIngredient: (id: number, ingredient: any) => apiClient.put(`/admin/ingredients/${id}`, ingredient),
  deleteIngredient: (id: number) => apiClient.delete(`/admin/ingredients/${id}`),
  getIngredient: (id: number) => apiClient.get(`/admin/ingredients/${id}`),
  getAllIngredients: () => apiClient.get('/admin/ingredients'),
  getIngredientsExpiringBefore: (date: string) => apiClient.get('/admin/ingredients/expiring-before', { params: { date } }),

  // Food safety records
  addFoodSafetyRecord: (record: any) => apiClient.post('/admin/food-safety', record),
  updateFoodSafetyRecord: (id: number, record: any) => apiClient.put(`/admin/food-safety/${id}`, record),
  deleteFoodSafetyRecord: (id: number) => apiClient.delete(`/admin/food-safety/${id}`),
  getFoodSafetyRecord: (id: number) => apiClient.get(`/admin/food-safety/${id}`),
  getAllFoodSafetyRecords: () => apiClient.get('/admin/food-safety'),
  getRecordsByIngredientId: (ingredientId: number) => apiClient.get(`/admin/food-safety/by-ingredient/${ingredientId}`),

  // Reassessment evaluations
  getAllEvaluations: () => apiClient.get('/admin/reassessment-evaluations'),
  getEvaluationById: (evaluationId: number) => apiClient.get(`/admin/reassessment-evaluations/${evaluationId}`),
  getEvaluationsByMemberId: (memberId: number) => apiClient.get(`/admin/reassessment-evaluations/member/${memberId}`),
};
