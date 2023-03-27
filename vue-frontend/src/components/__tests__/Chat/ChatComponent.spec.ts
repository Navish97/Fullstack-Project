import { mount } from "@vue/test-utils";
import {expect, it, vi} from "vitest";
import ChatComponent from "@/components/Chat/ChatComponent.vue";
import type { Message } from "@/types/MessageType";
import type { Item } from "@/types/ItemType";
import type { Chat } from "@/types/ChatType";
import MessageComponent from "@/components/Chat/MessageComponent.vue";
import { createPinia } from "pinia";
import * as MessagesService from '@/service/MessagesService';
import { useMessageStore } from "@/stores/Message";
import { useChatStore } from "@/stores/Chat";

let messages : Message[] = [
    {
        id: 1,
        sent: true,
        senderId:"2",
        senderName: "Recepient",
        message:"Hello tester one",
        chatId:1
    },
    {
        id: 2,
        sent: false,
        senderId:"1",
        senderName: "User",
        message:"Hello tester two",
        chatId:1
    },
];
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

const chat : Chat = {
    id: 1,
    userId: '1',
    userName: 'Recepient',
    userEmail: 'test@test.com',
    item:item
}
const pinia = createPinia();
let sendMessageSpy = vi.spyOn(MessagesService, 'sendMessage').mockImplementation(() => {
    return Promise.resolve({status:200, data:{
        message:{
            id: 3,
            sent: true,
            senderId:"2",
            senderName: "User",
            message:"Testing send message!",
            chatId:1
        },
        chatId:1
    }});
})
let wrapper = mount(ChatComponent, {
    props:{
        messages:messages,
        chat:chat
    },
    global:{
        plugins:[pinia]
    }
});
const messageStore = useMessageStore(wrapper.vm.$pinia);
const chatStore = useChatStore(wrapper.vm.$pinia);
messageStore.addMessage(messages[0]); 
messageStore.addMessage(messages[1]); 
chatStore.newChat(chat);

it("Test that the component renders successfully with correct props", () => {
    expect(wrapper.exists()).toBe(true);
    expect(wrapper.classes()).toContain("parent");
    expect(wrapper.find(".details").exists()).toBe(true);
    expect(wrapper.find(".message-bar").exists()).toBe(true);
});

it("Test that message component is passed correct prop and loads correctly", () => {
    const messageComponents = wrapper.findAllComponents(MessageComponent);

    expect(messageComponents).toHaveLength(messages.length);

    messageComponents.forEach((messageComponent, index) => {
        expect(messageComponent.props('message')).toEqual(messages[index]);
    })
});

it("Test that send message functionality works", async () => {
    wrapper.find('#messageInput').setValue('Testing send message!');
    await wrapper.find('.send-button').trigger("click");

    const messageComponents = wrapper.findAllComponents(MessageComponent);
    expect(messageComponents).toHaveLength(messages.length);
    messageComponents.forEach((messageComponent, index) => {
        expect(messageComponent.props('message')).toEqual(messages[index]);
    });


    expect(sendMessageSpy).toHaveBeenCalledOnce();
});

it("Test that send message functionality catches error and displays it", async () => {
    wrapper.find('#messageInput').setValue('Testing send message!');
    await wrapper.find('.send-button').trigger("click");
})





