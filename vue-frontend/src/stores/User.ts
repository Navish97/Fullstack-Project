import { defineStore } from 'pinia'
import type { Bookmark } from "@/types/BookmarkType";
import type {Item} from "@/types/ItemType";
import router from "@/router";
import axiosInstance from "@/service/AxiosInstance";
import {getUserBookmarks} from "@/service/BookmarkService";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        bookmarks: [] as Bookmark[],
        authenticated: false as boolean
    }),
    persist: {
        storage: sessionStorage,
    },
    getters: {
        getBookmarks: (state) => {
            return state.bookmarks
        },
        isItemBookmarked: (state) => (item: Item) => {
                return state.bookmarks.some(bookmark => bookmark.itemId === item.id);
        },
        addBookmark: (state) => (item: Item) => {
            //Axios call for adding bookmark to database
        },
        isLoggedIn: (state) => () => {
            return state.authenticated;
        }

    },
    actions: {
        logOut() {
            router.push("/")
            this.bookmarks = [];
            this.authenticated = false;
        },
        setLoggedIn(loggedIn: boolean) {
            this.authenticated = loggedIn
        },
        setBookmarks(bookmarks: Bookmark[]) {
            this.bookmarks = bookmarks;
        },
        async fetchBookmarks() {
            try {
                const response = await getUserBookmarks();
                const bookmarks: Bookmark[] = response.data;
                this.setBookmarks(bookmarks);
            } catch (error) {
                console.error('Error fetching bookmarks:', error);
            }
        },
        async checkAuthStatus() {
            try {
                if (this.isLoggedIn()) {
                    const response = await axiosInstance.get('/api/user-status');
                    this.authenticated = true; // Set authenticated to true on successful response
                }
            } catch (error) {
                console.error('Error checking authentication status:', error);
                this.authenticated = false; // Set authenticated to false on error
            }
        },
    }
})