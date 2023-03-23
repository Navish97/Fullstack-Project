import axiosInstance from "./AxiosInstance";

export async function getChats():Promise<any> {
    const response = await axiosInstance.get('/api/chats/load-chats')
    .then((response) => {
        return response;
    })
    .catch((error) => {
        console.log('Error retrieving chats', error.message);
    })
    return response;
}