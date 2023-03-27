import { mount } from "@vue/test-utils";
import { expect, it } from "vitest";
import ChatPreview from "@/components/Chat/ChatPreview.vue";

const chat = {
  id: 1,
  userId: '1',
  userName: 'Recepient',
  userEmail: 'test@test.com',
  item: {
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
  }
};

const wrapper = mount(ChatPreview, {
  props: {
    chat: chat,
  },
});

it("renders the user name", () => {
  const label = wrapper.find(".receive-label");
  expect(label.exists()).toBe(true);
  expect(label.text()).toContain(chat.userName);
});