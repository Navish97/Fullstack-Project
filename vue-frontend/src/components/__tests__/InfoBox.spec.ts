import { expect, it, beforeEach } from "vitest";
import { mount } from "@vue/test-utils";
import InfoBox from "@/components/profile/InfoBox.vue";
import { defineComponent } from "vue";

const TestComponent = defineComponent({
    components: { InfoBox },
    template: `<div>
          <info-box :title="title" :content="content" :icon="icon" />
      </div>`,

    setup() {

        const title = "Vitest title";
        const content = "Vitest content";

        return { title, content };
    },
});

let wrapper: any;

beforeEach(() => {
    wrapper = mount(TestComponent);
});


it("It properly renders the title", () => {
    const title = wrapper.find("h1");

    expect(title.text()).toEqual("Vitest title");
});

it("It properly renders content", () => {
    const content = wrapper.find("p");

    expect(content.text()).toEqual("Vitest content");
});