import { beforeEach, expect, it, vi, afterEach } from "vitest";
import { createPinia } from "pinia";
import { mount } from "@vue/test-utils";
import BookmarkButton from "@/components/Items/BookmarkButton.vue";
import { useItemStore } from "@/stores/Item";
import { useUserStore } from "@/stores/User";
import * as BookmarkService from "@/service/BookmarkService";

let wrapper: any;
let addBookmarkSpy: any;
let deleteBookmarkSpy: any;

beforeEach(() => {
    addBookmarkSpy = vi.spyOn(BookmarkService, "addBookmark").mockImplementation(() => {
        return Promise.resolve({ status: 200 });
    });

    deleteBookmarkSpy = vi.spyOn(BookmarkService, "deleteBookmark").mockImplementation(() => {
        return Promise.resolve({ status: 200 });
    });

    const pinia = createPinia();

    const itemStore = useItemStore(pinia);
    itemStore.setCurrentItemBookmarked(false);

    const userStore = useUserStore(pinia);
    userStore.setLoggedIn(true);

    wrapper = mount(BookmarkButton, { global: { plugins: [pinia] } });
});




it("It renders the button properly", () => {
    expect(wrapper.html()).toMatchSnapshot();
});


it("It toggles the bookmark state on button click", async () => {
    const itemStore = useItemStore(wrapper.vm.$pinia);
    itemStore.setCurrentItemBookmarked(false);

    await wrapper.find(".bookmark-button").trigger("click");
    expect(itemStore.isCurrentItemBookmarked).toBe(true);

    await wrapper.find(".bookmark-button").trigger("click");
    expect(itemStore.isCurrentItemBookmarked).toBe(false);
});

it("It displays the correct text based on bookmark state", () => {
    const itemStore = useItemStore(wrapper.vm.$pinia);

    itemStore.setCurrentItemBookmarked(false);
    wrapper = mount(BookmarkButton, { global: { plugins: [wrapper.vm.$pinia] } });

    expect(wrapper.text()).toContain("Add Bookmark");

    itemStore.setCurrentItemBookmarked(true);
    wrapper = mount(BookmarkButton, { global: { plugins: [wrapper.vm.$pinia] } });

    expect(wrapper.text()).toContain("Remove Bookmark");
});

it("It does not render the button if the user is not logged in", () => {
    const userStore = useUserStore(wrapper.vm.$pinia);
    userStore.setLoggedIn(false);

    wrapper = mount(BookmarkButton, { global: { plugins: [wrapper.vm.$pinia] } });

    expect(wrapper.find(".bookmark-button").exists()).toBe(false);
});
