<template>
  <div class="wrapper">
    <div class="grid-container">
      <div class="listing-type">
        <FilterComponent />
      </div>
      <div class="items">
        <ItemList :items="itemStore.items" :listingType="currentListingType" :currentPage="currentPage" :totalPages="totalPages" @pageup="pageUp" @pagedown="pageDown" />
      </div>
      <div class="right-sidebar">
        <ListingTypeButton />
        <div>test</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/Items/ItemList.vue';
import ListingTypeButton from '@/components/ButtonChangeListingType.vue';
import FilterComponent from '@/components/FilterComponent.vue';
import {computed, onMounted} from "vue";
import { useItemStore } from '@/stores/Item';
import { useUserStore } from '@/stores/User';
import { getItems } from '@/service/ItemService';
import { onBeforeRouteUpdate, useRoute } from 'vue-router';

const itemStore = useItemStore();
const userStore = useUserStore();
const route = useRoute();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});

let currentPage = 0;
let totalPages = 0;

function pageUp(){
  if(currentPage < totalPages){
    currentPage++;
    loadItems();
  }
}
function pageDown(){
  if(currentPage > 0){
    currentPage--;
    loadItems();
  }
}
async function loadItems(){
    await getItems(currentPage, 9, route.query)
    .then((response) => {
      itemStore.setLists(response.data.items);
      currentPage = response.data['current-page'];
      totalPages = response.data['total-pages']-1;
    })
    .catch((error) => {
      console.log(error);
    });
}
onBeforeRouteUpdate(async (to, from) => {
  console.log("route updated");
  loadItems();
})

onMounted(() => {
  loadItems();
})


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
