import apiClient from '../../axios'; // Your configured Axios instance
import type { VolunteerDTO } from '@/types/user/Index';
import type { TaskDTO } from '@/types/task/Index';

export const volunteerApi = {
  getProfile: (): Promise<{ data: VolunteerDTO }> => apiClient.get('/volunteer/profile'),
  getAssignedTasks: (): Promise<{ data: TaskDTO[] }> => apiClient.get('/volunteer/tasks'),
};
  