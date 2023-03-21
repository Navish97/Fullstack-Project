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
import {computed} from "vue";
import { useItemStore } from '@/stores/Item';

const itemStore = useItemStore();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});
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
  background-color: #f0f0f0;
}

.items {
  padding: 20px;
}

.right-sidebar {
  width: 250px;
  background-color: #f0f0f0;
  padding: 20px;
}
</style>




