import axios from "axios";

// Axios instance
const axiosInstance = axios.create({
    baseURL: "http://localhost:8080/api", // Adjust baseURL if needed
    headers: {
        "Content-Type": "application/json",
    },
});

// Request Interceptor (Attach JWT Token)
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    const publicRoutes = ["/api/donor/donate", "/api/auth/login", "/api/auth/register"];
    const isPublic = publicRoutes.some(route => config.url.includes(route));

    if (token && !isPublic) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    console.log("➡️ Request to:", config.url);
    console.log("➡️ Token attached?", !!config.headers.Authorization);

    return config;
  },
  (error) => Promise.reject(error)
);



axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    const originalRequest = error.config;

    // If 401 Unauthorized and not already retried
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      // Remove token
      localStorage.removeItem("token");
      localStorage.removeItem("userType");

      // Optional: show a message
      const message = error.response.data?.error || "Unauthorized. Please login again.";
      alert(message);

      // Redirect to login page
      window.location.href = "/login";
    }

    return Promise.reject(error);
  }
);

/*
// Placeholder response interceptor (for refresh token later)
axiosInstance.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;

        // Handle token expiry without crashing the app
        if(error.response?.status === 401 && !originalRequest._retry) {
            // In future: try refresh here

        }
        return Promise.reject(error);
    }
)
*/
export default axiosInstance;