<template>
  <div class="pagination">
    <a href="#" @click.prevent="emitNavPage(-1)" :class="{ disabled: props.currentPage === 1 }">&lt;&lt; Previous</a>
    <a v-if="props.currentPage > pageSize" 
      :href="getPageHref(1)" 
      @click.prevent="emitLoadPage(1)">1</a>
    <a v-if="props.currentPage > pageSize + 1" 
      @click.prevent="emitLoadPage(props.currentPage - pageSize)">...</a>
    <a v-for="page in visiblePages" 
    :key="page" :href="getPageHref(page)" 
    @click.prevent="emitLoadPage(page)" 
    :class="{ active: page === props.currentPage }">{{ page }}</a>
    <a v-if="props.pages.length > visiblePages[visiblePages.length - 1] + pageSize" 
      @click.prevent="emitLoadPage(props.currentPage + pageSize)">...</a>
    <a v-if="props.pages.length > visiblePages[visiblePages.length - 1]" 
      :href="getPageHref(props.pages.length)" 
      @click.prevent="emitLoadPage(props.pages.length)">{{ props.pages.length }}</a>
    <a href="#" 
    @click.prevent="emitNavPage(1)" 
    :class="{ disabled: props.currentPage === props.pages.length }">Next >></a>
  </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits, computed } from 'vue';

const props = defineProps({
  pages: {
    type: Array as () => number[],
    required: true,
  },
  currentPage: {
    type: Number,
    default: 1,
  },
});

const emit = defineEmits(['load-page', 'previous-page']);

function getPageHref(page: number): string {
  return `#page=${page}`;
}

function emitLoadPage(page: number) {
  emit('load-page', page);
}

function emitNavPage(direction: number) {
  if (
    (direction === -1 && props.currentPage === 1) ||
    (direction === 1 && props.currentPage === props.pages.length)
  ) {
    return; // do nothing if previous or next button is disabled
  }
  emit('previous-page', direction);
}

const visiblePages = computed(() => {
  const total = props.pages.length;
  const current = props.currentPage;
  const last = Math.ceil(total / pageSize) * pageSize;
  const start = Math.max(1, Math.floor((current - 1) / pageSize) * pageSize);
  const end = Math.min(start + pageSize - 1, last, total);
  const pages = [];
  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

let pageSize = window.innerWidth < 768 ? 3 : 10;

window.addEventListener('resize', () => {
  pageSize = window.innerWidth < 768 ? 3 : 10;
});

</script>

<style scoped>
.pagination {
  position: relative;
}

.pagination a {
  color: black;
  padding: 10px 18px;
  text-decoration: none;
}
.pagination a:hover:not(.active) {
  background-color: #031f3b;
  color: white;
}


.pagination a.active {
  background-color: cornflowerblue;
  color: white;
}

.pagination a.disabled {
  color: black;
  cursor: not-allowed;
  cursor: not-allowed;
  opacity: 0.6;
}

.pagination a.disabled:hover {
  background-color: inherit;
  color: inherit;
  cursor: not-allowed;
}

</style>