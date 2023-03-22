import axiosInstance from "@/service/AxiosInstance";

export async function getItems(pageNr : number, pageSize : number, filter : object): Promise<any> {
    const response = await axiosInstance.get('/api/items/page', {
        params: {
            pageNumber:pageNr,
            size:pageSize,
            filter:JSON.stringify(filter)
        }
    })
    return response
}

export async function getItemDetails(itemid: string): Promise<any> {
    return await axiosInstance.get('/api/item/details', {
        params: {
            id: itemid,
        }
    })
}