import axiosInstance from "@/service/AxiosInstance";

export async function deleteBookmark(itemId: number): Promise<any> {
    return await axiosInstance.delete(`/api/bookmarks/delete/${itemId}`);
}