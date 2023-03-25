import { beforeEach, expect, it, vi, afterEach } from "vitest";
import { createPinia } from "pinia";
import { mount } from "@vue/test-utils";
import LoginComponent from "@/components/Form/LoginForm.vue";
import { useUserStore } from "@/stores/User";
import * as AuthenticationService from "@/service/Authentication/AuthenticationService";
import BaseInput from "@/components/Form/BaseInput.vue";
import router from "@/router/index";

let wrapper: any;
let userStore: any;
let postLoginSpy: any;

beforeEach(() => {
    const pinia = createPinia();

    postLoginSpy = vi.spyOn(AuthenticationService, "postLogin")

    wrapper = mount(LoginComponent, {
        global: {
            components: {
                BaseInput,
            },
            plugins: [pinia, router],
        },
    });

    userStore = useUserStore(pinia);
    userStore.setLoggedIn(false);

});

afterEach(() => {
    wrapper.unmount();
    vi.resetAllMocks();
});


it("It navigates to home page on successful login", async () => {
    postLoginSpy.mockImplementation(() => {return Promise.resolve({ status: 200 });});

    wrapper.find("#inpEmail").setValue("test@test.com");
    wrapper.find("#inpPassord").setValue("123456");
    await wrapper.find("#button").trigger("click");

    expect(router.currentRoute.value.path).toBe("/");
});


it("It doesn't log in the user on failed login", async () => {
    postLoginSpy.mockImplementation(() => {return Promise.reject({ status: 403 });});

    wrapper.find("#inpEmail").setValue("test@test.com");
    wrapper.find("#inpPassord").setValue("123456");
    await wrapper.find("#button").trigger("click");

    expect(userStore.isLoggedIn()).toBe(false);
});
