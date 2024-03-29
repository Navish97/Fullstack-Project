import { defineStore } from 'pinia';
import type { Message } from '@/types/MessageType';

export const useMessageStore = defineStore({
    id: 'messages',
    state: () => ({
        chatid:-1 as number, 
        messages:[
        ] as Message[],
    }),
    getters: {
        getChatId: (state) => {
            return state.chatid;
        },
        getMessages: (state) => {
            return state.messages;
        }
    },
    actions: {
        setChatId(id: number){
            this.chatid = id;
        },
        setMessages(newMessages: []){
            this.messages = newMessages;
        },
        addMessage(message: Message){
            this.messages.push(message);
        }
    }
})