import {beforeEach, expect, it} from "vitest";
import {createPinia} from "pinia";
import {mount} from "@vue/test-utils";
import FilterComponent from "@/components/Filter/FilterComponent.vue";


let wrapper: any;

beforeEach(() => {

    const pinia = createPinia();

    wrapper = mount(FilterComponent, {
        global: {
            plugins: [pinia],
        },
    });
});

it("it renders the button properly", () => {
    expect(wrapper.html()).toMatchSnapshot();
});


