import apiClient from '../../axios';

export const partnerApi = {
  getMeals: () => apiClient.get('/partner/meals'),
  getDishes: () => apiClient.get('/partner/dishes'),
  getMenus: () => apiClient.get('/partner/menus'),
  getRiders: () => apiClient.get('/partner/riders'),
};
