export interface Item {
    id?: number;
    title: string;
    description: string;
    briefDescription: string;
    price: number;
    imageURLs: string[];
    categoryId: number;
    latitude: number;
    longitude: number;
    userId: number;
}