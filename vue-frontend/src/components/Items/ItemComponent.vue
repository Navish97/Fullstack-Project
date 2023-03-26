<template>
  <router-link class="item-link" :to="{ name: 'item-details', params: { id: item.id } }" :class="{ bookmarked: itemIsBookmarked }">
    <div class="container" :class="listingType" @click="updateSelected()">
      <div class="item" :class="listingType">
        <div class="image-wrapper">
          <img :src="bookmark" alt="bookmarked" class="bookmark-icon" v-if="itemIsBookmarked"/>
          <img :src="props.item.images[0].data.toString()" alt="thumbnail image" class="image"/>
          <div class="price"> {{ formattedPrice }} </div>
        </div>
        <div class="content-wrapper" :class="listingType">
          <div class="text-wrapper" :class="listingType">
            <h2> {{ item.title }} </h2>
            <h4 v-if="listingType==='list'"> {{ item.description }} </h4>
          </div>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script setup lang="ts">
import {defineProps, computed, ref} from 'vue';
import type { Item } from '@/types/ItemType';
import { useItemStore } from '@/stores/Item';
import {useUserStore} from "@/stores/User";
import bookmark from '@/assets/bookmark.png';

const itemStore = useItemStore();
const userStore = useUserStore();

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

const listingType = ref(props.listingType);

const itemIsBookmarked = computed(() => {
  return userStore.isItemBookmarked(props.item);
});

function updateSelected() {
  itemStore.setCurrentItem(props.item);
}

const formattedPrice = computed(() => {
  return `${props.item.price.toFixed(0)} Kr`;
});

</script>

<style scoped>
.container {
  color: white;
}
@media (min-width: 768px) {
  .container:hover {
    cursor: pointer;
  }

  .container.thumbnail {
    width: 100%;
    height: 100%;
    display: grid;
    grid-template-columns: 1fr;
    align-self: center;
    align-content: center;
    align-items: center;
    gap: 10px;
    border: rgba(28, 27, 27, 0.35) 1px solid;
    border-radius: 8px;
    box-shadow: 0 8px 12px rgba(0, 0, 0, 0.4), 8px 4px 4px rgba(0, 0, 0, 0.1);
  }

  .item.thumbnail {
    width: 240px;
    height: 240px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 16px;
    padding: 0 0 0px;
    border-radius: 8px;
  }

  .container.thumbnail:hover {
    background-color: #bebebe;
    border-radius: 8px;
  }

  .container.list:hover {
    background-color: #bebebe;
    border-radius: 8px;
  }

  .container.list {
    width: 100%;
    display: grid;
    grid-template-columns: 1fr;
    align-items: center;
    align-content: center;
  }

  .item.list {
    box-shadow: 0 8px 12px rgba(0, 0, 0, 0.4), 8px 4px 4px rgba(0, 0, 0, 0.1);
    border: rgba(28, 27, 27, 0.5) 1px solid;
    width: 100%;
    height: 160px;
    display: grid;
    grid-template-columns: 180px 2fr;
    border-radius: 8px;

  }

  .item.list .image-wrapper {
    align-self: center;
    justify-self: center;
    width: 90%;
    height: 90%;
    object-fit: cover;
  }

  .item.list .image-wrapper img:not(.bookmark-icon) {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    max-height: 100%;
  }

  .item.thumbnail .image-wrapper {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .item.list .content-wrapper {
    position: relative;
    top: 0;
    margin: 5px;
  }

  .item.thumbnail .image-wrapper img:not(.bookmark-icon) {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    max-height: 100%;
  }

  .content-wrapper {
    word-wrap: normal;
    overflow: hidden;
    overflow-wrap: break-word;
    text-overflow: clip;
    word-break: break-word;
    white-space: normal;
  }

  .bookmark-icon {
    position: absolute;
    top: 0;
    right: 0;
    z-index: 1;
    width: 35px;
    height: 50px;
    opacity: 0.6;
    border-top-right-radius: 8px;
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
}



@media (max-width: 768px) {
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

  .content-wrapper.list {
    position:relative;
    overflow: hidden;
    word-break: break-word;
    word-wrap: break-word;
    max-height: 100%;
    line-height: 1.5;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 3;
  }

  .content-wrapper.thumbnail {
    max-width: 170px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    padding-right: .2rem;
  }

  .image-wrapper img:not(.bookmark-icon) {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    max-height: 100%;
  }

  .text-wrapper.thumbnail {
    position:relative;
    word-wrap: normal;
    overflow: hidden;
    overflow-wrap: normal;
    text-overflow: clip;
    word-break: normal;
    white-space: normal;
    align-self: start;
    padding: 0rem 1rem .5rem 1rem;
  }

  h2 {
    font-size: 1.2rem;
  }

  .container.thumbnail:hover {
    background-color: transparent;
    width: 100%;
    height: 100%;
  }

  .container.thumbnail {
    padding-top: 4%;
  }

  .bookmark-icon {
    position: absolute;
    top: 0;
    right: 0;
    z-index: 1;
    width: 35px;
    height: 50px;
    opacity: 0.6;
    border-top-right-radius: 8px;
  }

  .container.list {
    width: 100%;
    max-height: 15%;
  }

  .item.thumbnail .image-wrapper {
    width: 92%;
    height: 88%;
    display: flex;
    justify-content: center;
    align-items: center;
    object-fit: cover;
  }

  .item.list {
    width: 98%;
    height: 20%;
    display: grid;
    padding: 2%;
  }

  .container.list:hover {
    background-color: transparent;
  }

  .container {
    box-shadow: 0 8px 12px rgba(0, 0, 0, 0.4), 8px 4px 4px rgba(0, 0, 0, 0.1);
    display: grid;
    border: rgba(28, 27, 27, 0.5) 1px solid;
    grid-template-rows: 1fr 0fr;
    border-radius: 8px
  }

  .container.thumbnail {
    display: grid;
    grid-template-columns: 100% 100%;
    max-width: 100%;
    height: 100%;
  }

  .item .list{
    width: 100%;
    height: 100%;
  }


  .item.thumbnail {
    max-width: 100%;
    height: 200px;
    display: flex;
    flex-direction: column;
    align-content: center;
    align-items: center;
    border-radius: 8px;
  }

  .item.list {
    width: 100%;
    max-height: 100%;
    display: grid;
    grid-template-columns: 140px 2fr;
    align-items: center;
  }

  .item.list .image-wrapper {
    width: 120px;
    height: 120px;
    object-fit: cover;
  }
}
</style>