import { defineStore } from 'pinia';
import type { Chat } from '@/types/ChatType';
import type { Item } from '@/types/ItemType';

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
        },
        newChat(chat : Chat){
            if(this.findChatById(-1) === undefined){
                this.removeChat(-1);
            }
            this.chats.push(chat);
        },
        findChatById(id: number): Chat | undefined {
            return this.chats.find(chat => chat.id === id);
        },
        removeChat(id: number) {
            const index = this.chats.findIndex(chat => chat.id === id);
            if (index !== -1) {
                this.chats.splice(index, 1);
            }
        },
        findExistingChat(chat: Chat): Chat | undefined {
            return this.chats.find((c) => c.userId == chat.userId 
            && c.userName === chat.userName 
            && c.userEmail === chat.userEmail 
            && c.item.id === chat.item.id);
          }

}});