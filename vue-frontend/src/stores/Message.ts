import { defineStore } from 'pinia';
import type { Message } from '@/types/MessageType';

export const useMessageStore = defineStore({
    id: 'messages',
    state: () => ({
        chatid:9999999 as number, 
        messages:[
            {
                id: 1,
                senderId: "user1",
                receiverId: "user2",
                message: "Hi there, how are you doing?",
                chatId: 12345
              },
              {
                id: 2,
                senderId: "user2",
                receiverId: "user1",
                message: "I'm doing well, thanks for asking!",
                chatId: 12345
              },
              {
                id: 3,
                senderId: "user1",
                receiverId: "user3",
                message: "Hey, I was wondering if you're free tonight?",
                chatId: 67890
              },
              {
                id: 4,
                senderId: "user3",
                receiverId: "user1",
                message: "Sorry, I already have plans. Maybe another time?",
                chatId: 67890
              },
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
        }
    }
})