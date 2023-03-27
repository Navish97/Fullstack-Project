import { mount } from "@vue/test-utils";
import {beforeEach, expect, it, vi} from "vitest";
import ChatDetails from "@/components/Chat/ChatDetails.vue";
import type { Item } from "@/types/ItemType";
import { createPinia } from "pinia";
import { useMessageStore } from '@/stores/Message';
import { useChatStore } from '@/stores/Chat';

let wrapper:any;
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
    userName: 'test',
    userEmail: 'test@test.com'
};
beforeEach(() => {
    const pinia = createPinia();
    wrapper = mount(ChatDetails, {
        global:{
            plugins:[pinia]
        },
        props:{
            chat:{
                id: 1,
                userId: '1',
                userName: 'test',
                userEmail: 'test',
                item:item
            }
        }
    });
    const messageStore = useMessageStore();
    const chatStore = useChatStore();
})

it('Component renders item title and seller name correctly', () => {
    expect(wrapper.find('.item-title').text()).toContain('test item');
    expect(wrapper.find('.item-seller').text()).toContain('test');
});




