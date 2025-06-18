import apiClient from '../../axios';
import type { DishDTO } from '@/types/meal/Index';

export const dishApi = {
  getAll: (): Promise<{ data: DishDTO[] }> => apiClient.get('/dishes'),
  getById: (id: number): Promise<{ data: DishDTO }> => apiClient.get(`/dishes/${id}`),
  getByMealId: (mealId: number): Promise<{ data: DishDTO[] }> => apiClient.get(`/dishes/meal/${mealId}`),
  getByMenuId: (menuId: number): Promise<{ data: DishDTO[] }> => apiClient.get(`/dishes/menu/${menuId}`),
  create: (dish: DishDTO): Promise<{ data: DishDTO }> => apiClient.post('/dishes', dish),
  update: (id: number, dish: DishDTO): Promise<{ data: DishDTO }> => apiClient.put(`/dishes/${id}`, dish),
  delete: (id: number): Promise<void> => apiClient.delete(`/dishes/${id}`),
  uploadPhoto: (id: number, file: File): Promise<{ data: string }> => {
    const formData = new FormData();
    formData.append('file', file);
    return apiClient.post(`/dishes/${id}/upload-photo`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
  },
  getPhoto: (id: number): Promise<Blob> => apiClient.get(`/dishes/${id}/photo`, { responseType: 'blob' }),
};
