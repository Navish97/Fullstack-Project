import axios from 'axios';
import type { AxiosInstance, AxiosResponse } from 'axios';
import type {LoginData} from "@/types/LoginData";
import type {RegisterData} from "@/types/RegisterData";
import type {User} from "@/types/UserType";


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

const userApi: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api',
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
});

export const getUserData = async (): Promise<User | null> => {
    try {
        const response: AxiosResponse<User> = await userApi.get('/my-profile');
        console.log(response)
        const { name, email } = response.data;
        return { id: response.data.id, name, email, roles: response.data.roles };
    } catch (error) {
        console.error(error);
        return null;
    }
};