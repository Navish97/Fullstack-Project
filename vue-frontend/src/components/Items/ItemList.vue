<template>
  <div class="item-list">
    <div v-if="listingType==='list'" class="item-list">
      <ItemComponent v-for="item in items" :item="item" :key="item.id" listing-type="list" />
    </div>
    <div v-else-if="listingType==='thumbnail'" class="item-grid">
      <ItemComponent v-for="item in items" :item="item" :key="item.id" listing-type="thumbnail" />
    </div>
  </div>
  <PaginationComponent 
  :pages="props.pages" 
  @load-page="(page) => emitLoadPage(page)" 
  @previous-page="(direction) => navigatePage(direction)"
  class = "pager"
  :current-page="props.currentPage"/>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import type { Item } from '@/types/ItemType';
import ItemComponent from '@/components/Items/ItemComponent.vue';
import PaginationComponent from './PaginationComponent.vue';
import { directive } from '@babel/types';
const props = defineProps({
  items: {
    type: Array as () => Item[],
    required: true
  },
  listingType: {
    type: String,
    default: 'thumbnail'
  },
  currentPage: {
    type: Number,
    default: 1,
  },
  pages: {
    type: Array as () => number[],
    required: true,
  },
});

const emit = defineEmits(["load-page",'nav-page']);
function navigatePage(pageNav:number){
  emit('nav-page', pageNav);
}
function emitLoadPage(page:number){
  emit("load-page", page);
}
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
.page-count{
  color: black;
  font-weight: bolder;
  font-size: 120%;
  vertical-align: center;
  text-align: center;
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
.page-buttons{
  padding-top: 1.5rem;
  padding-bottom: 3rem;
  display: flex;
  width: inherit;
  justify-content: center;
}
.pager{
  padding-top: 1.5rem;
  padding-bottom: 3rem;
  display: flex;
  width: inherit;
  justify-content: center;
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