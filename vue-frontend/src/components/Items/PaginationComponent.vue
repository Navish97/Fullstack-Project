<template>
    <div class = "pagination">
        <a href="#" @click.prevent="emitNavPage(-1)" :class="{ disabled: props.currentPage === 1 }">&lt;&lt; Previous</a>
        <a 
        v-for="page in pages" 
        :key="page" 
        :href="getPageHref(page)" 
        @click.prevent="emitLoadPage(page)"
        :class ="{ 'active': page === props.currentPage }">
         {{  page }}</a>
        <a href="#" @click.prevent="emitNavPage(1)" :class="{ disabled: props.currentPage === props.pages.length }"> Next >></a>
    </div>
</template>

<script setup lang = "ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  pages: {
    type: Array as () => number[],
    required: true,
  },
  currentPage: {
    type: Number,
    default: 1,
  },
}
);

const emit = defineEmits(['load-page', 'previous-page']);
function getPageHref(page: number): string {
    return `#page=${page}`;
  }

function emitLoadPage(page : number){
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
</script>

<style scoped>
.pagination {
    position: relative;
  }
 
  /* pagination styling */
  .pagination a {
    color: black;
    padding: 10px 18px;
    text-decoration: none;
  }
 
  /* pagination hover effect on non-active */
  .pagination a:hover:not(.active) {
    background-color: #031F3B;
    color: white;
  }
 
  /* pagination hover effect on active*/
 
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