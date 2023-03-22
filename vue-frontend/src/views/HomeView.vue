<template>
  <div class="wrapper">
    <div class="grid-container">
      <div class="listing-type">
        <FilterComponent />
      </div>
      <div class="items">
        <ItemList :items="itemStore.items" :listingType="currentListingType"/>
      </div>
      <div class="right-sidebar">
        <ListingTypeButton />
        <div>test</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/ItemList.vue';
import ListingTypeButton from '@/components/ButtonChangeListingType.vue';
import FilterComponent from '@/components/FilterComponent.vue';
import {computed, onMounted} from "vue";
import { useItemStore } from '@/stores/Item';
import { useUserStore } from '@/stores/User';
import { getItems } from '@/service/ItemService';


const itemStore = useItemStore();
const userStore = useUserStore();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});

onMounted(() => {
  loadPage();
  console.log(userStore.isLoggedIn());
});


async function loadPage(){
  await getItems(0,15, {
    minPrice:null,
    maxPrice:null,
    usedValue:true,
    newValue:true,
  })
  .then((response) => {
    console.log(response);
    console.log(response.data);
    console.log(response.data.items)
    itemStore.setLists(response.data.items);
  })
  .catch((error) => {
    console.log(error);
    console.log('Error loading items:', error.message);
    console.log('Error details:', error.response.data);
  })
}
</script>

<style>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.grid-container {
  display: grid;
  grid-template-columns: auto 60% auto;
  grid-gap: 20px;
  justify-content: center;
  width: 80%;
}

.listing-type {
  width: 250px;
  padding: 20px;
}


.items {
  padding: 20px;
}

.right-sidebar {
  width: 250px;
  padding: 20px;
}
</style>
