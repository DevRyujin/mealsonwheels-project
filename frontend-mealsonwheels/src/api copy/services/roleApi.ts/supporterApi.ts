import apiClient from '../../axios'; // Your configured Axios instance

export const supporterApi = {
  getProfile: () => apiClient.get('/supporter/profile'),
};
