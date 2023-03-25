import {beforeEach, expect, it} from "vitest";
import {createPinia} from "pinia";
import {mount} from "@vue/test-utils";
import ListingTypeButton from "@/components/ButtonChangeListingType.vue";


let wrapper: any;

beforeEach(() => {

    const pinia = createPinia();

    wrapper = mount(ListingTypeButton, {
        global: {
            plugins: [pinia],
        },
    });
});

it("it renders the button properly", () => {
    expect(wrapper.html()).toMatchSnapshot();
});


