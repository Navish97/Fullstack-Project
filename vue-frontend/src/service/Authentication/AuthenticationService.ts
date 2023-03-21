import axios from 'axios';
import type { AxiosInstance, AxiosResponse } from 'axios';
import type {LoginData} from "@/types/LoginData";
import type {RegisterData} from "@/types/RegisterData";


const authenticateAPI: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api/auth',
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});

export const postLogin = (login: LoginData): Promise<AxiosResponse> => {
    return authenticateAPI.post('/login', login);
};

export const postRegister = (registerData: RegisterData): Promise<AxiosResponse> => {
    return authenticateAPI.post('/register', registerData);
};