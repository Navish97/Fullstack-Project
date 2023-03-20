<template>
  <bookmark-component />
  <div class="items" v-if="selectedItem">
    <ItemDetails :item="selectedItem" />
  </div>
</template>

<script setup lang = "ts">
import ItemDetails from '@/components/ItemDetails.vue';
import { useItemStore } from '@/stores/Item';
import {computed} from "vue";
import BookmarkComponent from "@/components/BookmarkComponent.vue";

const itemStore = useItemStore();

const selectedItem = computed(() => {
  if (itemStore.getCurrentItem !== undefined) {
    return itemStore.getCurrentItem;
  }
});
</script>

<style scoped>

</style>