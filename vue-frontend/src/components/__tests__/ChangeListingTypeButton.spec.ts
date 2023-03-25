import {expect, it} from "vitest";
import {mount} from "@vue/test-utils";
import ListingTypeButton from "@/components/ButtonChangeListingType.vue";
import {createPinia} from "pinia";

it("renders the button with initial thumbnail configuration", () => {
    const pinia = createPinia();
    const wrapper = mount(ListingTypeButton, {
        global: {
            plugins: [pinia],
        },
    });

    
    const button = wrapper.find(".list-button");
    expect(button.text()).toEqual("thumbnail");
});

it("changes listing type to list when clicked", async () => {
    const pinia = createPinia();
    const wrapper = mount(ListingTypeButton, {
        global: {
            plugins: [pinia],
        },
    });


    const button = wrapper.find(".list-button");
    await button.trigger("click");
    expect(wrapper.vm.currentListingType).toEqual("list");
});

it("changes back to list when clicked twice", async () => {
    const pinia = createPinia();
    const wrapper = mount(ListingTypeButton, {
        global: {
            plugins: [pinia],
        },
    });


    const button = wrapper.find(".list-button");
    await button.trigger("click"); // Change to the last listing type
    await button.trigger("click");
    expect(wrapper.vm.currentListingType).toEqual("thumbnail"); // Change this to match the first listing type
});
