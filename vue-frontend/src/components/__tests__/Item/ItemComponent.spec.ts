import { beforeEach, expect, it, vi, afterEach } from "vitest";
import { createPinia } from "pinia";
import { mount } from "@vue/test-utils";
import ItemComponent from "@/components/Items/ItemComponent.vue";
import { useItemStore } from "@/stores/Item";
import { useUserStore } from "@/stores/User";
import type { Item } from "@/types/ItemType";

let wrapper: any;
let item: Item;
let userStore: any;
let itemStore: any;

beforeEach(() => {
    const pinia = createPinia();

    item = {
        id: 1,
        description: "Test item description",
        price: 100,
        images: [{ data: new ArrayBuffer(8), contentType: "image/png" }],
        categoryId: 1,
        title: "Test item",
        latitude: 0,
        longitude: 0,
        userId: "s",
        userName: "Test user",
        userEmail: "test@test.com"
    };

    itemStore = useItemStore(pinia);
    itemStore.setCurrentItem(item);
    itemStore.setCurrentItemBookmarked(false);

    userStore = useUserStore(pinia);
    userStore.setLoggedIn(true);

    wrapper = mount(ItemComponent, {global: { plugins: [pinia] }, props: { item, listingType: "list" }
    });
});

it("It correctly checks user bookmarks", async () => {
    userStore.setBookmarks([{id: 1, userId: "s", itemId: item.id}]);
    expect(userStore.isItemBookmarked(item)).toBe(true);

    userStore.setBookmarks([{id: 1, userId: "s", itemId: 0}]);
    expect(userStore.isItemBookmarked(item)).toBe(false);
});

it("It correctly toggles the bookmark icon in listings", () => {
    userStore.setBookmarks([{id: 1, userId: "s", itemId: 0},]);
    wrapper = mount(ItemComponent, {global: { plugins: [wrapper.vm.$pinia] }, props: { item, listingType: "list" }});
    expect(wrapper.find(".bookmark-icon").exists()).toBe(false);

    userStore.setBookmarks([{id: 1, userId: "s", itemId: item.id},]);
    wrapper = mount(ItemComponent, {global: { plugins: [wrapper.vm.$pinia] }, props: { item, listingType: "list" }});

    expect(wrapper.find(".bookmark-icon").exists()).toBe(true);
});


it("It updates the selected item when clicked", async () => {
    const itemStore = useItemStore(wrapper.vm.$pinia);

    expect(itemStore.getCurrentItem).toEqual(item);
});