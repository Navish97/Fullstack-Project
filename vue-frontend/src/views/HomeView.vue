<template>
  <div class="content">
    <div class="items">
      <ItemList :items="itemStore.items" :listingType="'list'" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/ItemList.vue';
import { useItemStore } from '@/stores/Items';

const itemStore = useItemStore();
</script>

