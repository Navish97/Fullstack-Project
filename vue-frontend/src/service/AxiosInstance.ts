import axios, { AxiosInstance } from "axios";

const baseURL = window.location.hostname === "localhost" ? "http://localhost:8080" : "https://fullstack-project-xt5ws57jza-lz.a.run.app";

const axiosInstance: AxiosInstance = axios.create({
    baseURL,
    withCredentials:true,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});

export default axiosInstance;