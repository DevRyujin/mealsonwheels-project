import apiClient from '../../axios';

export const caregiverApi = {
  getProfile: () => apiClient.get('/caregiver/profile'),
  updateProfile: (updatedInfo: any) => apiClient.put('/caregiver/profile', updatedInfo),
  getAssignedMembers: () => apiClient.get('/caregiver/members'),
  assignMemberToSelf: (memberId: number) => apiClient.post(`/caregiver/assign-member/${memberId}`),
};
