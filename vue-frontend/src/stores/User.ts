import { defineStore } from 'pinia'
import type { Bookmark } from "@/types/BookmarkType";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        loggedInUser: "" as string,
        bookmarks: [] as Bookmark[],
    }),
    persist: {
        storage: sessionStorage,
    },
    getters: {
        getLoggedInUser: (state) => {
            return state.loggedInUser;
        },
        getBookmarks: (state) => {
            return state.bookmarks
        },
        isItemBookmarked: (state) => (itemId: number) => {
            return state.bookmarks.some(bookmark => bookmark.itemId === itemId);
        }
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