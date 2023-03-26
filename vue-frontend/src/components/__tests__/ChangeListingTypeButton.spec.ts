import { expect, it } from "vitest";
import { mount } from "@vue/test-utils";
import ListingTypeButton from "@/components/ButtonChangeListingType.vue";
import { createPinia } from "pinia";
import { beforeEach } from 'vitest'

let wrapper: any;
beforeEach(() => {

    const pinia = createPinia();


    wrapper = mount(ListingTypeButton, {
        global: {
            plugins: [pinia],
        },
    });
});

it("renders the button with initial thumbnail configuration", () => {
    const button = wrapper.find(".list-button");

    expect(button.text()).toEqual("thumbnail");
});

it("changes listing type to list when clicked", async () => {
    const button = wrapper.find(".list-button");
    await button.trigger("click");

    expect(wrapper.vm.currentListingType).toEqual("list");
});

it("changes back to list when clicked twice", async () => {
    const button = wrapper.find(".list-button");
    await button.trigger("click");
    await button.trigger("click");

    expect(wrapper.vm.currentListingType).toEqual("thumbnail");
});
