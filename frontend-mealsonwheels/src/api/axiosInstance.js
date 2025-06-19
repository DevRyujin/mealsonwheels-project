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
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return(config);
    },
    (error) => Promise.reject(error)
);

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

export default axiosInstance;