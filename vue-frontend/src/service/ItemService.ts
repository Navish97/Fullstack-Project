import axiosInstance from "@/service/AxiosInstance";

export async function getItems(pageNr : number, pageSize : number, filter : object): Promise<any> {
    const response = await axiosInstance.get('/api/items/page', {
        params: {
            pageNumber:pageNr,
            size:pageSize,
            filter:JSON.stringify(filter)
        }
    }).then((response) => {
        return response;
    })
    .catch((error) => {
        console.log('Error loading items: ', error.message)
    })
    return response;
}

export async function getItemDetails(itemId: String): Promise<any> {
    return await axiosInstance.get(`/api/items/details/${itemId}`);
}

export async function getMyItems(pageNr: number, pageSize: number): Promise<any> {
    return await axiosInstance.get('/api/items/my-listings', {
        params: {
            pageNumber: pageNr,
            size: pageSize,
        }
    });
}