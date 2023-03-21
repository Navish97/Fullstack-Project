<template>
  <div class="container">
    <button @click="changeListingIndex">{{currentListingType}}</button>
  </div>
</template>

<script setup lang="ts">
import { useItemStore } from '@/stores/Item';
import {computed} from "vue";

const itemStore = useItemStore();
let listingIndex = 0;

function changeListingIndex() {
  if (listingIndex+1 < itemStore.getListingTypes.length) {
    listingIndex++;
  } else {
    listingIndex = 0;
  }
  itemStore.setCurrentListingType(itemStore.getListingTypes[listingIndex]);
}

const currentListingType = computed(() => {
  return itemStore.getCurrentListingType;
});

</script>

<style scoped>

.container {
  padding-top: 20px;
}
button {
  width: 100px;
  background-color: rgba(30, 29, 29, 0.99);
  color: #ffffff;
  padding: 12px 16px;
  border-radius: 4px;
  border: 1px solid #646464;
  font-size: 14px;
  cursor: pointer;
  text-align: center;
}

button:hover {
  background-color: #555;
}
</style>