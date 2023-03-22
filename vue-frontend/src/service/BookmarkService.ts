import axiosInstance from "@/service/AxiosInstance";

export async function deleteBookmark(itemId: number): Promise<any> {
    return await axiosInstance.delete(`/api/bookmarks/delete/${itemId}`);
}

export async function addBookmark(itemId: number): Promise<any> {
    return await axiosInstance.post(`/api/bookmarks/add/${itemId}`);
}

export async function getUserBookmarks(): Promise<any> {
    return await axiosInstance.get(`/api/bookmarks/user`);
}