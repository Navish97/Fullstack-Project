export interface Item {
    id: number;
    title: string;
    description: string;
    price: number;
    imageURLs: string[];
    categoryId: number;
    latitude: number;
    longitude: number;
    userId: string;
    userName: string;
    userEmail: string;
}