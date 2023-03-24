import {defineStore} from 'pinia'
import type {Item} from "@/types/ItemType";

export const useItemStore = defineStore({
  id: 'items',
  state: () => ({
    listingTypes: ['thumbnail', 'list'] as string[],
    currentListingType: 'thumbnail' as string,
    currentItem: {} as Item,
    currentItemBookmarked: false as boolean,
    persist: {
      storage: sessionStorage,
    },
    items : [
   ] as Item[],
    NewListingCategory: 0 as number,
  }),
  getters: {
    isCurrentItemBookmarked: (state): boolean => {
      return state.currentItemBookmarked;
    },
    getCurrentItem: (state) => {
      return state.currentItem;
    },
    getCurrentItemId: (state) => {
        return state.currentItem.id;
    },
    getCurrentListingType: (state) => {
      return state.currentListingType;
    },
    getImages: (state) => {
        return state.currentItem.images;
    },
    getListingTypes: (state) => {
      return state.listingTypes;
    },
    getItems: (state) => {
      return state.items;
    },
    getNewListingCategory: (state) => {
      return state.NewListingCategory;
    }
  },
  actions: {
    setCurrentItemBookmarked(bookmarked: boolean) {
        this.currentItemBookmarked = bookmarked;
    },
    setCurrentItem(item: Item) {
      this.currentItem = item;
    },
    setCurrentListingType(listingType: string) {
      this.currentListingType = listingType;
    },
    addItem(item: Item) {
      this.items.push(item);
    },
    responseToItem(response: any) {
      const data = JSON.parse(JSON.stringify(response));
      const images = data.images.map((image: any) => ({
        data: image.data,
        contentType: image.contentType
      }));
      return {
        id: data.id,
        description: data.description,
        price: data.price,
        images: images,
        categoryId: data.categoryId,
        title: data.title,
        latitude: parseFloat(data.latitude),
        longitude: parseFloat(data.longitude),
        userId: data.userId
      };
    },
    setLists(list : []){
      const newItems:Item[] = [];
      list.forEach((element) => {
        const newItem = this.responseToItem(element);
        newItems.push(newItem);
      });
      this.items = newItems;
    },
    setNewListingCategory(categoryId: number){
      this.NewListingCategory = categoryId;
    }
  },
});
