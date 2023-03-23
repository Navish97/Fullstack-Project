import { defineStore } from 'pinia';
import type { Chat } from '@/types/ChatType';

export const useChatStore = defineStore({
    id:'chats',
    state: () => ({
        chats : [
            { chatid: 1, receiver: 'John', item: 1 },
            { chatid: 2, receiver: 'Mary', item: 2 },
            { chatid: 3, receiver: 'Alex', item: 3 }
        ] as Chat[],
    }),
    getters: {
        getChats: (state) => {
            return state.chats;
        }
    },
    actions: {
        setChats(newChats : []){
            this.chats = newChats;
        }
    },

})