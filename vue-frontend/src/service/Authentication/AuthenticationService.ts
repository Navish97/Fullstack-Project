import type { AxiosResponse } from 'axios';
import type {LoginData} from "@/types/LoginData";
import type {RegisterData} from "@/types/RegisterData";
import type {User} from "@/types/UserType";
import axiosInstance from "@/service/AxiosInstance";

export const postLogin = async (login: LoginData): Promise<AxiosResponse> => {
    return axiosInstance.post('/api/auth/login', login);
};

export const postRegister = async (registerData: RegisterData): Promise<AxiosResponse> => {
    return axiosInstance.post('/api/auth/register', registerData);
};

export const getUserData = async (): Promise<User | null> => {
    try {
        const response: AxiosResponse<User> = await axiosInstance.get('/api/my-profile');
        console.log(response)
        const { name, email } = response.data;
        return { id: response.data.id, name, email, roles: response.data.roles };
    } catch (error) {
        console.error(error);
        return null;
    }
};