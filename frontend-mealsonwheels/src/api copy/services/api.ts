// src/api/api.ts

import apiClient from '../axios';

export const api = {
  health: () => apiClient.get('/health'),
  test: () => apiClient.get('/test'),
  // Add core endpoints if needed
};
