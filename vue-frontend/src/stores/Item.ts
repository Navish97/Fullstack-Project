import { defineStore } from 'pinia'
import type { Item } from "@/types/ItemType";

export const useItemStore = defineStore({
  id: 'items',
  state: () => ({
    listingTypes: ['thumbnail', 'list'] as string[],
    currentListingType: 'thumbnail' as string,
    currentItem: {} as Item,
    items : [
   ] as Item[],
    currentCategory: "" as string,
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
    getCurrentCategory: (state) => {
      return state.currentCategory;
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
      let newItems:Item[] = [];
      list.forEach((element) => {
        const data = JSON.parse(JSON.stringify(element));
        const newItem = {
          id: data.id,
          description: data.description,
          briefDescription: data.briefDescription,
          price: data.price,
          imageURLs: [data.imageUrls],
          categoryId: data.category.id,
          title: data.title,
          latitude: parseFloat(data.latitude),
          longitude: parseFloat(data.longitude),
          userId: data.user.id,
        }
        newItems.push(newItem);
      });
      this.items = newItems;
    },
    setCurrentCategory(category: string) {
      this.currentCategory = category;
    }
  },
});
