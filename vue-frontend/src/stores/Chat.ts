import { defineStore } from 'pinia';
import type { Chat } from '@/types/ChatType';

export const useChatStore = defineStore({
    id:'chats',
    state: () => ({
        chats : [
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