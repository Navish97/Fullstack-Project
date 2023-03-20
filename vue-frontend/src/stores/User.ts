import { defineStore } from 'pinia'
import type { Bookmark } from "@/types/BookmarkType";
import type {User} from "@/types/UserType";
import type {Item} from "@/types/ItemType";

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        loggedInUser: {} as User,
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
        isItemBookmarked: (state) => (item: Item) => {
                return state.bookmarks.some(bookmark => bookmark.itemId === item.id);
        },
        addBookmark: (state) => (item: Item) => {
            //Axios call for adding bookmark to database
        }

    },
    actions: {
        setLoggedInUser(user: User) {
            this.loggedInUser = user;
        },
        setBookmarks(bookmarks: Bookmark[]) {
            this.bookmarks = bookmarks;
        }
    }
})