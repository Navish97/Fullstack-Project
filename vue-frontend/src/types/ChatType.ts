import type { Item } from '@/types/ItemType';
export interface Chat {
    id: number;
    userId: string,
    userName:string,
    userEmail:string,
    item: Item;
}