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
        console.log(error.response.headers)
        if (error.response.headers['error-message']) {
            alert("Old password is incorrect. Please try again.");
            return;
        }
        else if (error.response.status === 401 && useUserStore().isLoggedIn) {
            console.log("Session has expired. You've been logged out.");
            alert("Session has expired. You've been logged out.");
            useUserStore().logOut()
        }
        else if(error.response.status !== 401) {
            console.log("An error has occurred. Errorcode: " + error.response.status);
        }
        return Promise.reject(error);
    }
);
export default axiosInstance;