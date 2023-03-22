<template>
  <router-link class="item-link" :to="{ name: 'item-details', params: { id: item.id } }">
    <div class="container" :class="listingType" @click="updateSelected()">
      <div class="item" :class="listingType">
        <div class="image-wrapper">
          <img :src="item.imageURLs[0]" alt="item.briefDescription"/>
          <div class="price"> {{ formattedPrice }} </div>
        </div>
        <div class="content-wrapper">
          <h2> {{ item.title }} </h2>
          <h4 v-if="listingType==='list'"> {{ item.briefDescription }} </h4>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import type { Item } from '@/types/ItemType';
import { useItemStore } from '@/stores/Item';
const itemStore = useItemStore();

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

function updateSelected() {
  itemStore.setCurrentItem(props.item);
}

const formattedPrice = computed(() => {
  return `${props.item.price.toFixed(0)} Kr`;
});

</script>

<style scoped>
.container:hover {
  cursor: pointer;
}

.container.thumbnail:hover {
  padding-top: 1px;
  width: 272px;
  height: 272px;
  background-color: #505050;
  border-radius: 8px;
}

.container.list:hover {
  width: 600px;
  height: 176px;
  background-color: #505050;
  border-radius: 8px;
}

.item.thumbnail {
  width: 240px;
  height: 240px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 16px;
  padding: 0 0 16px;
  border-radius: 8px;
}

.item.list {
  width: 600px;
  height: 160px;
  display: grid;
  grid-template-columns: 180px 2fr;
  margin: 16px;
  padding: 16px 0 0;
  border-radius: 8px;
}

.item.list .image-wrapper {
  width: 160px;
  height: 100%;
  object-fit: cover;
}

.item.thumbnail .image-wrapper {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item.list .content-wrapper {
  padding-top: 0;
  padding-left: 16px;
  padding-right: 32px;
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
  border-radius: 8px;
}

.item .price {
  position: absolute;
  bottom: 0;
  right: 0;
  padding: 8px;
  background-color: rgba(0, 0, 0, 0.44);
  color: #fff;
  font-size: 16px;
  border-bottom-left-radius: 8px;
  border-top-left-radius: 8px;
}
</style>