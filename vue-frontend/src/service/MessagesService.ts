import axiosInstance from "./AxiosInstance";
import type { Chat } from '@/types/ChatType';

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

export async function getMessages(chat : Chat):Promise<any> {
    console.log(chat);
    const response = await axiosInstance.get('/api/chats/load-messages', {
        params: {
            chatId : chat.id
        },
    })
    .then((response) => {
        return response;
    })
    .catch((error) => {
        console.log('Error retrieving chats', error.message);
    })
    return response;
}

export async function sendMessage(message: String, chatId:number):Promise<any> {
    const response = axiosInstance.post('/api/chats/send-message', {message:message, chatId:chatId})
    return response;
}