
import { expect, it, beforeEach } from "vitest";
import { mount } from "@vue/test-utils";
import { ref } from "vue";
import {createPinia} from "pinia";
import ProfileBox from "@/components/Profile/ProfileBox.vue";

let wrapper: any;

beforeEach(() => {

    const pinia = createPinia();

    wrapper = mount(ProfileBox, {
        global: {
            plugins: [pinia],
        },
    });
});

it("it renders the profile wrapper component properly", () => {
  const profileWrapper = wrapper.find(".profile-wrapper");

  expect(profileWrapper.exists()).toBe(true);
});

it("it renders the log out button properly", () => {
  const logOutButton = wrapper.find(".log-out-btn");

  expect(logOutButton.exists()).toBe(true);
});

