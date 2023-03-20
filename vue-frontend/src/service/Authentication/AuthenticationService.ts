import axios from "axios";

const authenticateAPI = axios.create({
    baseURL: 'http://localhost:8080/api/auth',
    withCredentials: true,
    headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
    }
})

export const postLogin = (login) => {
    return authenticateAPI.post('/login', login)
}

export const postRegister = (registerData) => {
    return authenticateAPI.post('/register', registerData);
};