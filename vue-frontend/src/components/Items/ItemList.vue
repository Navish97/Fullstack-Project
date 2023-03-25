<template>
  <div class="item-list">
    <div v-if="listingType==='list'" class="item-list">
      <ItemComponent v-for="item in items" :item="item" :key="item.id" listing-type="list" />
    </div>
    <div v-else-if="listingType==='thumbnail'" class="item-grid">
      <ItemComponent v-for="item in items" :item="item" :key="item.id" listing-type="thumbnail" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import type { Item } from '@/types/ItemType';
import ItemComponent from '@/components/Items/ItemComponent.vue';
import PaginationComponent from './PaginationComponent.vue';
const props = defineProps({
  items: {
    type: Array as () => Item[],
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
  display: grid;
  grid-template-columns: repeat(1,95%);
  gap: 1rem;
  justify-content: center;
}
img {
  background-color: transparent;
  height: 80%;
  width: 90%;
  vertical-align: center;
}

.arrow {
  background-color: transparent;
  border: 0;
  width: 3%;
  height: 10%;
}
.arrow :hover {
  transform: scale(1.1);
}

.item-grid {
  display: grid;
  gap: 1rem;
  grid-template-columns: repeat(3, 1fr);
}

@media (max-width: 768px) {
  .item-grid {
    display: grid;
    width: 100%;
    grid-template-columns: repeat(2, 1fr);
  }

  .item-list {
    height: 100%;
    display: grid;
    grid-auto-rows: auto;
    align-items: center;
    gap: .5rem;
  }

  .container.thumbnail:hover,
  .container.list:hover {
    background-color: transparent;
  }

  .item-grid{
    grid-template-columns: repeat(2, 49%);
    gap: 2%;
  }
}
</style>