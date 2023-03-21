<template>
  <div class="container">
    <div class="content">
      <div class="listing-type">
        <ListingTypeButton />
      </div>
      <div class="items">
        <ItemList :items="itemStore.items" :listingType="currentListingType"/>
      </div>
      <div class ="filter">
        <FilterComponent />
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
import {getItems} from '@/service/ItemService';


const itemStore = useItemStore();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});

onMounted(() => {
  loadPage();
});

async function loadPage(){
  await getItems(0,1)
  .then((response) => {
    console.log(response);
    itemStore.setLists(response.data.items);
  })
  .catch((error) => {
    console.log(error);
  })
}
</script>

<style>
.content {
  margin-top: 25px;
  display: grid;
  grid-template-columns: 125px 100%;
  justify-items: start;
}

</style>