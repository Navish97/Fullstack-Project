import type {Image} from "@/types/ImageType";

export interface Item {
    id: number;
    title: string;
    description: string;
    price: number;
    images: Image[];
    categoryId: number;
    latitude: number;
    longitude: number;
    userId: string;
    userName: string;
    userEmail: string;
}