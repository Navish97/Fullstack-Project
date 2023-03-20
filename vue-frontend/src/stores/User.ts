import { defineStore } from 'pinia'
import type { Bookmark } from "@/types/BookmarkType";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        loggedInUser: "" as string,
        bookmarks: [] as Bookmark[],
    }),

    getters: {
        getLoggedInUser: (state) => {
            return state.loggedInUser;
        },
        getBookmarks: (state) => {
            return state.bookmarks
        },
    },
    actions: {
        setLoggedInUser(userEmail: string) {
            this.loggedInUser = userEmail;
        },
        setBookmarks(bookmarks: Bookmark[]) {
            this.bookmarks = bookmarks;
        }
    }
})