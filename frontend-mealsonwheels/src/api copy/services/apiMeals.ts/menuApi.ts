import apiClient from '../../axios';
import type { MenuDTO } from '@/types/meal/Index';

export const menuApi = {
  getAll: (): Promise<{ data: MenuDTO[] }> => apiClient.get('/menus'),
  getById: (id: number): Promise<{ data: MenuDTO }> => apiClient.get(`/menus/${id}`),
  create: (menu: MenuDTO): Promise<{ data: MenuDTO }> => apiClient.post('/menus', menu),
  update: (id: number, menu: MenuDTO): Promise<{ data: MenuDTO }> => apiClient.put(`/menus/${id}`, menu),
  delete: (id: number): Promise<void> => apiClient.delete(`/menus/${id}`),
};
