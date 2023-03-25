<template>
  <div class="pagination">
    <a href="#" @click.prevent="emitNavPage(-1)" :class="{ disabled: props.currentPage === 1 }">&lt;&lt; Previous</a>
    <a v-if="props.currentPage > 10"
      :href="getPageHref(1)"
      @click.prevent="emitLoadPage(1)">1</a>
    <a v-if="props.currentPage > 11"
      @click.prevent="emitLoadPage(props.currentPage - 10)">...</a>
    <a v-for="page in visiblePages"
    :key="page" :href="getPageHref(page)"
    @click.prevent="emitLoadPage(page)"
    :class="{ active: page === props.currentPage }">{{ page }}</a>
    <a v-if="props.pages.length > visiblePages[visiblePages.length - 1] + 1"
      @click.prevent="emitLoadPage(props.currentPage + 10)">...</a>
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
  const last = Math.ceil(total / 10) * 10;
  const start = Math.max(1, Math.floor((current - 1) / 10) * 10);
  const end = Math.min(start + 9, last, total);
  const pages = [];
  for (let i = start; i <= end; i++) {
    pages.push(i);
  }
  return pages;
});

</script>

<style scoped>
.pagination {
  display: flex;
  justify-content: center;
}

.pagination a {
  color: black;
  padding: 10px 12px;
  text-decoration: none;
  text-align: center;
  min-width: 30px;
  box-sizing: border-box;
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
  opacity: 0.6;
}

.pagination a.disabled:hover {
  background-color: inherit;
  color: inherit;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .pagination a {
    padding: 8px 10px;
    font-size: 12px;
  }
}

</style>