import apiClient from '../../axios';
import type { MealDTO } from '@/types/meal/Index';

export const mealApi = {
  getAll: (): Promise<{ data: MealDTO[] }> => apiClient.get('/meals'),
  getById: (id: number): Promise<{ data: MealDTO }> => apiClient.get(`/meals/${id}`),
  create: (meal: MealDTO): Promise<{ data: MealDTO }> => apiClient.post('/meals', meal),
  update: (id: number, meal: MealDTO): Promise<{ data: MealDTO }> => apiClient.put(`/meals/${id}`, meal),
  delete: (id: number): Promise<void> => apiClient.delete(`/meals/${id}`),
  uploadPhoto: (id: number, file: File): Promise<{ data: string }> => {
    const formData = new FormData();
    formData.append('file', file);
    return apiClient.post(`/meals/${id}/upload-photo`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
  },
  getPhoto: (id: number): Promise<Blob> =>
    apiClient.get(`/meals/${id}/photo`, { responseType: 'blob' }),
};
