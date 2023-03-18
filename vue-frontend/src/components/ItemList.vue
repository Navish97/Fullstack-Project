<template>
  <div class="item-list">
    <div v-if="listingType==='list'">
      <ItemComponent v-for="item in items" :item="item" :key="item.id" listing-type="list" />
    </div>
    <div v-else-if="listingType==='thumbnail'" class="item-grid">
      <ItemComponent v-for="item in items" :item="item" :key="item.id" listing-type="thumbnail" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import { ItemType } from '@/types/ItemType.ts';
import ItemComponent from '@/components/ItemComponent.vue';

const props = defineProps({
  items: {
    type: Array as () => ItemType[],
    required: true
  },
  listingType: {
    type: String,
    default: 'thumbnail'
  }
});
</script>

<style scoped>
.item-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.item-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  margin: 32px;
}
</style>