<template>
  <div class="content">
    <div class="about">
      <ItemDetails :item="itemData[0]" />
    </div>
    <div class="about">
      <ItemList :items="itemData" :listingType="'thumbnail'" />
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/ItemList.vue';
import ItemDetails from '@/components/ItemDetails.vue';
import { useItemStore } from '@/stores/Items';

const itemData = useItemStore().items;
</script>

