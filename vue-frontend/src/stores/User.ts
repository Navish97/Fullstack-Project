import { defineStore } from 'pinia'
import type { Bookmark } from "@/types/BookmarkType";
import type {Item} from "@/types/ItemType";
import router from "@/router";
import axiosInstance from "@/service/AxiosInstance";
import {getUserBookmarks} from "@/service/BookmarkService";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        loggedInId: '' as string,
        bookmarks: [] as Bookmark[],
        authenticated: false as boolean,
        role: '' as string,
    }),
    persist: {
        storage: sessionStorage,
    },
    getters: {
        getLoggedInId: (state) => {
            return state.loggedInId;
        },
        getRole: (state) => {
            return state.role;
        },
        getBookmarks: (state) => {
            return state.bookmarks
        },
        isItemBookmarked: (state) => (item: Item) => {
                return state.bookmarks.some(bookmark => bookmark.itemId === item.id);
        },
        isLoggedIn: (state) => () => {
            return state.authenticated;
        }

    },
    actions: {
        setLoggedInId(id: string) {
            this.loggedInId = id;
        },
        setRole(role: string) {
            this.role = role;
        },
        logOut() {
            router.push("/")
            this.bookmarks = [];
            this.authenticated = false;
            this.loggedInId = '';
            this.role = '';
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