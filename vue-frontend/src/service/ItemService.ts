import axiosInstance from "@/service/AxiosInstance";
import { useItemStore } from "@/stores/Item";



export async function getItems(pageNr : number, pageSize : number, filter : object): Promise<any> {
    const itemStore = useItemStore();
    const response = await axiosInstance.get('/api/items/page', {
        params: {
            pageNumber:pageNr,
            size:pageSize,
            filter:JSON.stringify(filter)
        }
    }).then((response) => {
        itemStore.setLists(response.data.items);
    })
    .catch((error) => {
        console.log('Error loading items: ', error.message)
    })
}

