import apiClient from '../../axios';

export const donorApi = {
  getProfile: () => apiClient.get('/donor/profile'),
  donate: (amount: number) => apiClient.post('/donor/donate', null, { params: { amount } }),
};
