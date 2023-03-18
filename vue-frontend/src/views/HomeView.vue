<template>
  <div class="content">
    <div class="about" v-if="selectedItem">
      <ItemDetails :item="selectedItem" />
    </div>
    <div class="about">
      <ItemList :items="itemStore.items" :listingType="'thumbnail'" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/ItemList.vue';
import ItemDetails from '@/components/ItemDetails.vue';
import { useItemStore } from '@/stores/Items';
import {computed} from "vue";

const itemStore = useItemStore();

const selectedItem = computed(() => {
  if (itemStore.currentItem !== undefined) {
    return itemStore.currentItem;
  }
});
</script>

