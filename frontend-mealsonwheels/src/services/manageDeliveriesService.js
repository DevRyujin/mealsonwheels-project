import api from "../api/axiosInstance";

export const manageDeliveriesService = {
  getMembersWithOrders: async () => {
    const res = await api.get("/admin/members-with-orders");
    return res.data;
  },

  getCaregiverWithOrders: async () => {
    const res = await api.get("/admin/caregivers-with-orders");
    return res.data;
  },

  getApprovedRiders: async () => {
    const res = await api.get("/admin/approved-riders");
    return res.data; // Must return an array of riders with riderId, riderName, riderEmail
  },

  assignToRider: async (orderId, riderId) => {
    const res = await api.post(`/admin/assign-rider/${orderId}`, { riderId });
    return res.data;
  }
};
