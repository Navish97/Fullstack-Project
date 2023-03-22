import { defineStore } from 'pinia'
import type { Item } from "@/types/ItemType";

export const useItemStore = defineStore({
  id: 'items',
  state: () => ({
    listingTypes: ['thumbnail', 'list'] as string[],
    currentListingType: 'thumbnail' as string,
    currentItem: {} as Item,
    persist: {
      storage: sessionStorage,
    },
    items : [
   ] as Item[],
    NewListingCategory: 0 as number,
  }),
  getters: {
    getCurrentItem: (state) => {
      return state.currentItem;
    },
    getCurrentItemId: (state) => {
        return state.currentItem.id;
    },
    getCurrentListingType: (state) => {
      return state.currentListingType;
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
    setCurrentItem(item: Item) {
      this.currentItem = item;
    },
    setCurrentListingType(listingType: string) {
      this.currentListingType = listingType;
    },
    addItem(item : Item){
      const currentItems = this.items;
      const updatedItems = [...currentItems, item];
      this.items = updatedItems;
    },
    setLists(list : []){
      const newItems:Item[] = [];
      list.forEach((element) => {
        const data = JSON.parse(JSON.stringify(element));
        const newItem = {
          id: data.id,
          description: data.description,
          briefDescription: data.briefDescription,
          price: data.price,
          imageURLs: [data.imageUrls],
          categoryId: data.categoryid,
          title: data.title,
          latitude: parseFloat(data.latitude),
          longitude: parseFloat(data.longitude),
          userId: data.userid,
        }
        newItems.push(newItem);
      });
      this.items = newItems;
    },
    setNewListingCategory(categoryId: number){
      this.NewListingCategory = categoryId;
    }
  },
});
