import { defineStore } from 'pinia'
import type { Bookmark } from "@/types/BookmarkType";
import type {User} from "@/types/UserType";
import type {Item} from "@/types/ItemType";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        loggedInUserEmail: "" as string,
        bookmarks: [] as Bookmark[],
    }),
    persist: {
        storage: sessionStorage,
    },
    getters: {
        getLoggedInUserEmail: (state) => {
            return state.loggedInUserEmail;
        },
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
            return state.loggedInUserEmail !== "";
        }

    },
    actions: {
        logOut() {
            this.loggedInUserEmail = "";
            this.bookmarks = [];
        },
        setLoggedInUserEmail(userEmail: string) {
            this.loggedInUserEmail = userEmail;
        },
        setBookmarks(bookmarks: Bookmark[]) {
            this.bookmarks = bookmarks;
        },
    }
})