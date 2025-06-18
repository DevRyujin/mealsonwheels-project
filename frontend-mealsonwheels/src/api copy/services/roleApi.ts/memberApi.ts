import apiClient from '../../axios';

export const memberApi = {
  getProfile: () => apiClient.get('/member/profile'),
  updateProfile: (updatedInfo: any) => apiClient.put('/member/profile', updatedInfo),
  getAssignedCaregiver: () => apiClient.get('/member/caregiver'),
  submitOrderFeedback: (orderId: number, feedbackData: any) => 
    apiClient.post(`/member/order-feedback/${orderId}`, feedbackData),
};
