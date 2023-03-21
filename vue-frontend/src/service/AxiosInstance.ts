import axios from "axios";
import type { AxiosInstance } from "axios";
import { useUserStore } from "@/stores/User";
const baseURL = window.location.hostname === "localhost" ? "http://localhost:8080" : "https://fullstack-project-xt5ws57jza-lz.a.run.app";

const axiosInstance: AxiosInstance = axios.create({
    baseURL,
    withCredentials:true,
    timeout: 5000,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});
axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response.status === 401) {
            useUserStore().logOut()
            alert("Session expired, please login again")
        }

        return Promise.reject(error);
    }
);
export default axiosInstance;