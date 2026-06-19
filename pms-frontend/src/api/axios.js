import axios from "axios";
import { toast } from "react-toastify";
const api = axios.create({
    baseURL: "http://localhost:8080/api",
});

api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

        return config;
    },
    (error) => Promise.reject(error),
);
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response) {
      const message = error.response.data?.message || "Something went wrong";

      toast.error(message);
    } else {
      toast.error("Server is unreachable");
    }

    return Promise.reject(error);
  },
);
export default api;
