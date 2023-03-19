<template>
  <div class="container">
    <button @click="changeListingIndex">{{currentListingType}}</button>
  </div>
</template>

<script setup lang="ts">
import { useItemStore } from '@/stores/Items';
import {computed} from "vue";

const itemStore = useItemStore();
let listingIndex = 0;

function changeListingIndex() {
  if (listingIndex === 0) {
    listingIndex = 1;
  } else {
    listingIndex = 0;
  }
  itemStore.currentListingType = itemStore.listingTypes[listingIndex];
}

const currentListingType = computed(() => {
  return itemStore.currentListingType;
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