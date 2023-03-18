export interface Item {
    id: number;
    title: string;
    description: string;
    briefDescription: string;
    price: number;
    thumbnail: string;
    category: string;
    latitude: number;
    longitude: number;
    seller: {
        name: string;
        email: string;
    };
}