import { defineStore } from 'pinia'
import type { User } from "@/types/UserType";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        user: {} as User,
        bookmarks: [] as number[],
    }),

    getters: {
        getLoggedInUser: (state) => {
            return state.user
        }
    },

    actions: {
        setLoggedInUser(user: User) {
            this.user = user
        },
    }
})