import { defineStore } from 'pinia'
import type { Item } from "@/types/ItemType";

export const useItemStore = defineStore({
  id: 'items',
  state: () => ({
    listingTypes: ['thumbnail', 'list'] as string[],
    currentListingType: 'thumbnail' as string,
    currentItem: {} as Item,
    items : [{
      id: 1,
      description: 'This is the full description of the item of item id 1',
      briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      price: 249,
      imageURLs: ['https://media.istockphoto.com/id/166041214/photo/cayaks-on-the-moraine-lake.jpg?s=612x612&w=0&k=20&c=zSS-OgqEFnafrbfMHeFSn-KiAqJJTW-kgXZzf1yn8oA=', "https://images.unsplash.com/photo-1500964757637-c85e8a162699?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YmVhdXRpZnVsJTIwbGFuZHNjYXBlfGVufDB8fDB8fA%3D%3D&w=1000&q=80", "https://www.nyip.edu/media/zoo/images/when-to-use-natural-light-1_5a93b56987b7c16527210985524889f8.jpg"],
      categoryId: 1,
      title: 'Landscape Portrait',
      latitude: 37.7749,
      longitude: -122.4194,
      userId: 1,
      },
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 399,
        imageURLs: ['https://images.unsplash.com/photo-1500964757637-c85e8a162699?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YmVhdXRpZnVsJTIwbGFuZHNjYXBlfGVufDB8fDB8fA%3D%3D&w=1000&q=80'],
        categoryId: 2,
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        userId: 1,
      },
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 449,
        imageURLs: ['https://photonterrace.net/en/photon/behavior/file/716/double-rainbow.jpg'],
        categoryId: 3,
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        userId: 1,
      },
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 129,
        imageURLs: ['https://www.nyip.edu/media/zoo/images/when-to-use-natural-light-1_5a93b56987b7c16527210985524889f8.jpg'],
        categoryId: 1,
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        userId: 1,
      },
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 159,
        imageURLs: ['https://merriam-webster.com/assets/mw/images/gallery/gal-wap-slideshow-slide/aurora-borealis-7071-bb1ac685e1fecf3a7b7bcd8abac175e6@1x.jpg'],
        categoryId: 1,
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        userId: 1,
      }
   ] as Item[],
  }),
  getters: {
    getCurrentItem: (state) => {
      return state.currentItem;
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
  },
  actions: {
    setCurrentItem(item: Item) {
      this.currentItem = item;
    },
    setCurrentListingType(listingType: string) {
      this.currentListingType = listingType;
    },
  },
});
