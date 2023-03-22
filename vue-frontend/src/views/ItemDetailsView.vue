<template>
  <div class="items" v-if="selectedItem">
    <ItemDetails :item="selectedItem" />
  </div>
</template>

<script setup lang = "ts">
import ItemDetails from '@/components/Items/ItemDetails.vue';
import { useItemStore } from '@/stores/Item';
import {computed} from "vue";

const itemStore = useItemStore();

const selectedItem = computed(() => {
  if (itemStore.getCurrentItem !== undefined) {
    return itemStore.getCurrentItem;
  }
});
</script>

<style scoped>

</style>