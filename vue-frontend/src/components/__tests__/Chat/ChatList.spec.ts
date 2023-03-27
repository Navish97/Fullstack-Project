
import { mount } from "@vue/test-utils"
import {expect, it, vi} from "vitest";
import ChatList from "@/components/Chat/ChatList.vue";
import type { Chat } from "@/types/ChatType";
import type { Item } from "@/types/ItemType";
import ChatPreview from "@/components/Chat/ChatPreview.vue";

const item : Item = {
    id: 1,
    title:'test item',
    description:'test description',
    price:1,
    images: [],
    categoryId: 1,
    latitude: 1,
    longitude: 1,
    userId: '1',
    userName: 'Recepient',
    userEmail: 'test@test.com'
};
const itemTwo : Item = {
    id: 2,
    title:'test item 2',
    description:'test description 2',
    price:1,
    images: [],
    categoryId: 1,
    latitude: 1,
    longitude: 1,
    userId: '1',
    userName: 'Recepient',
    userEmail: 'test@test.com'
};
const chats : Chat [] = [
        {
            id: 1,
            userId: '1',
            userName: 'Recepient',
            userEmail: 'test@test.com',
            item:item
        },
        {
            id: 2,
            userId: '2',
            userName: 'Recepient 2',
            userEmail: 'test2@test.com',
            item:itemTwo
        },
]
let wrapper = mount(ChatList, {props:{
    chats : chats,
    selectedChatId : 1
}});

it("Component loading chats with given chat props", () => {
    expect(wrapper.exists()).toBe(true);
    expect(wrapper.find(".convo-wrapper").exists()).toBe(true);
    expect(wrapper.find(".chatelement").exists()).toBe(true);

    const chatPreviews = wrapper.findAllComponents(ChatPreview);
    expect(chatPreviews).toHaveLength(chats.length);
    chatPreviews.forEach((chatPreview, index) => {
        expect(chatPreview.props('chat')).toEqual(chats[index]);
    })
});

it("Clicking on ChatPreview emits 'chat-clicked' event with the chat data", async () => {
    const chatPreviews = wrapper.findAllComponents(ChatPreview);
    expect(chatPreviews).toHaveLength(chats.length);

    for (let i = 0; i < chatPreviews.length; i++) {
        const chatPreview = chatPreviews[i];
        const chat = chats[i];

        await chatPreview.trigger('click');
        expect(wrapper.emitted()).toHaveProperty('chat-clicked');
        console.log(wrapper.emitted('chat-clicked'));
        expect(wrapper.emitted('chat-clicked')).toHaveLength(i+1);
        expect(wrapper.emitted('chat-clicked')![i]).toEqual([chat]);
    }
});

