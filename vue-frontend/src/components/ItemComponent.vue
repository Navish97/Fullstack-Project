<template>
  <div class="container" :class="listingType">
    <div class="item" :class="listingType">
      <div class="image-wrapper">
        <img :src="item.thumbnail" alt="item.briefDescription"/>
        <div class="price"> {{ formattedPrice }} </div>
      </div>
      <div class="content-wrapper">
        <h2> {{ item.title }} </h2>
        <h4 v-if="listingType==='list'"> {{ item.briefDescription }} </h4>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import { Item } from '@/types';

const props = defineProps({
  item: {
    type: Object as () => Item,
    required: true
  },
  listingType: {
    type: String,
    default: 'thumbnail'
  }
});

const formattedPrice = computed(() => {
  return `${props.item.price.toFixed(0)} Kr`;
});

</script>

<style scoped>
.container.thumbnail:hover {
  width: 17rem;
  height: 17rem;
  background-color: #505050;
  border-radius: 0.5rem;
}

.container.list:hover {
  width: 30rem;
  height: 12rem;
  background-color: #505050;
  border-radius: 0.5rem;
}
.item.thumbnail {
  width: 15rem;
  height: 15rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 1rem;
  padding: 0 0 1rem;
  border-radius: 0.5rem;
}

.item.list {
  width: 30rem;
  height: 10rem;
  display: grid;
  grid-template-columns: 1fr 2fr;
  margin: 1rem;
  padding: 1rem 0 0;
  border-radius: 0.5rem;
}

.item .image-wrapper {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item.list .content-wrapper {
  padding-top: 0rem;
  padding-left: 1rem;
  padding-right: 2rem;
  width: 100%;
  word-wrap: normal;
  overflow: hidden;
  overflow-wrap: break-word;
  text-overflow: clip;
}

.item img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 0.5rem;
}

.item .price {
  position: absolute;
  bottom: 0;
  right: 0;
  padding: 0.5rem;
  background-color: rgba(0, 0, 0, 0.44);
  color: #fff;
  font-size: 1rem;
  border-bottom-left-radius: 0.5rem;
  border-top-left-radius: 0.5rem;
}
</style>