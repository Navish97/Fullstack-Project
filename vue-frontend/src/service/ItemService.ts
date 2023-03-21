import axios from "axios";

const itemAPI = axios.create({
    baseURL: 'http://localhost:8080/api/items',
    withCredentials:true,
    headers: {
        Accept:'application/json',
        'Content-Type':'application/json',
    }
})

export async function getItems(pageNr : number, pageSize : number): Promise<any> {
    const response = await itemAPI.get('/page', {
        params: {
            pageNumber:pageNr,
            size:pageSize,
        }
    })
    return response
}

