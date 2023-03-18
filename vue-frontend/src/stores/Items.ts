import { defineStore } from 'pinia'
import type {Item} from "@/types/ItemType";

export const useItemStore = defineStore({
  id: 'items',
  state: () => ({
    currentItem: {} as Item,
    items : [{
      id: 1,
      description: 'This is the full description of the item of item id 1',
      briefDescription: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
      price: 10.99,
      imageURLs: ['https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg', "https://images.unsplash.com/photo-1500964757637-c85e8a162699?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YmVhdXRpZnVsJTIwbGFuZHNjYXBlfGVufDB8fDB8fA%3D%3D&w=1000&q=80"],
      category: 'Electronics',
      title: 'Landscape Portrait',
      latitude: 37.7749,
      longitude: -122.4194,
      seller: {
        name: 'John Doe',
        email: 'johndoe@example.com'
      }},
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 10.99,
        imageURLs: ['https://images.unsplash.com/photo-1500964757637-c85e8a162699?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YmVhdXRpZnVsJTIwbGFuZHNjYXBlfGVufDB8fDB8fA%3D%3D&w=1000&q=80'],
        category: 'Electronics',
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        seller: {
          name: 'John Doe',
          email: 'johndoe@example.com'
        }},
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 10.99,
        imageURLs: ['https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg'],
        category: 'Electronics',
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        seller: {
          name: 'John Doe',
          email: 'johndoe@example.com'
        }},
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 10.99,
        imageURLs: ['https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg'],
        category: 'Electronics',
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        seller: {
          name: 'John Doe',
          email: 'johndoe@example.com'
        }},
      {
        id: 2,
        description: 'This is the full description of the item',
        briefDescription: 'This is a brief description of the item',
        price: 10.99,
        imageURLs: ['https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg'],
        category: 'Electronics',
        title: 'Landscape Portrait',
        latitude: 37.7749,
        longitude: -122.4194,
        seller: {
          name: 'John Doe',
          email: 'johndoe@example.com'
        }
      }
   ] as Item[],
  }),
  actions: {
    setCurrentItem(item: Item) {
      this.currentItem = item;
    },
  },
});
