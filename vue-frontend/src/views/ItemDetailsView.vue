<template>
  <div class="items" v-if="selectedItem">
    <ItemDetails :item="selectedItem" />
  </div>
</template>

<script setup lang = "ts">
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

<style scoped>

</style>