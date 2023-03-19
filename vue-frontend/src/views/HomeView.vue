<template>
  <div class="container">
    <div class="content">
      <div class="listing-type">
        <ListingTypeButton />
      </div>
      <div class="items">
        <ItemList :items="itemStore.items" :listingType="currentListingType"/>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/ItemList.vue';
import ListingTypeButton from '@/components/ButtonChangeListingType.vue';
import {computed} from "vue";
import { useItemStore } from '@/stores/Items';

const itemStore = useItemStore();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});
</script>

<style>

</style>