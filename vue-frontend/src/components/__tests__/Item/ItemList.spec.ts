import { mount } from "@vue/test-utils";
import {expect, it, vi} from "vitest";
import ItemList from "@/components/Items/ItemList.vue";
import type { Item } from "@/types/ItemType";
import { createPinia } from "pinia";
import { useItemStore } from "@/stores/Item";
import ItemComponent from "@/components/Items/ItemComponent.vue";
import type { Image } from "@/types/ImageType";
const image : Image = {
    data: new ArrayBuffer(1),
    contentType:'png'
}
const items : Item[] = [
    {
        id: 1,
        title:'test item',
        description:'test description',
        price:1,
        images: [image],
        categoryId: 1,
        latitude: 1,
        longitude: 1,
        userId: '1',
        userName: 'user',
        userEmail: 'test@test.com'
    },
    {
        id: 2,
        title:'test item 2',
        description:'test description 2',
        price:1,
        images: [image],
        categoryId: 1,
        latitude: 1,
        longitude: 1,
        userId: '2',
        userName: 'user 2',
        userEmail: 'test2@test.com'
    },
];
const pinia = createPinia();
let wrapper = mount(ItemList, {
    global:{
        plugins:[pinia]
    }
});
const itemStore = useItemStore(wrapper.vm.$pinia);
itemStore.items = items;
wrapper.setProps({
    pages:[1,2],
    items: itemStore.items,
    listingType:'thumbnail'
})


it("Test that items from props loads thumbnail style", () => {
    const itemComps = wrapper.findAllComponents(ItemComponent);

    expect(itemComps).toHaveLength(items.length);
    itemComps.forEach((itemComp, index) => {
        expect(itemComp.props('item')).toEqual(items[index]);
        expect(itemComp.props('listingType')).toEqual('thumbnail');
    })

});

it("Test that items from props loads list style", async () => {
    await wrapper.setProps({
        pages:[1,2],
        items: itemStore.items,
        listingType:'list'
    });
    await itemStore.setCurrentListingType('list');
    const itemComps = wrapper.findAllComponents(ItemComponent);

    expect(itemComps).toHaveLength(items.length);
    itemComps.forEach((itemComp, index) => {
        expect(itemComp.props('item')).toEqual(items[index]);
        expect(itemComp.props('listingType')).toEqual('list');
    })

});

