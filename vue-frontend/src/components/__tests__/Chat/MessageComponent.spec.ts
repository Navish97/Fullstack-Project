import MessageComponent from "@/components/Chat/MessageComponent.vue";
import { mount } from "@vue/test-utils";
import {expect, it, vi} from "vitest";

let wrapper = mount(MessageComponent, {
    props:{
        message:{
            id:1,
            sent:true,
            senderId:"1",
            senderName:"test",
            message:"test",
            chatId:1,
        }
    }
});


it("Renders sender name and message with valid prop", () => {
    expect(wrapper.find('.from').text()).toContain("test");
    expect(wrapper.find('.message').text()).toContain("test");
})






