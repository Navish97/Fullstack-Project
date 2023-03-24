<template>
  <router-link class="item-link" :to="{ name: 'item-details', params: { id: item.id } }" :class="{ bookmarked: itemIsBookmarked }">
    <div class="container" :class="listingType" @click="updateSelected()">
      <div class="item" :class="listingType">
        <div class="image-wrapper">
          <img :src="bookmark" alt="bookmarked" class="bookmark-icon" v-if="itemIsBookmarked"/>
          <img :src="props.item.images[0].data" alt="thumbnail image" class="image"/>
          <div class="price"> {{ formattedPrice }} </div>
        </div>
        <div class="content-wrapper">
          <h2> {{ item.title }} </h2>
          <h4 v-if="listingType==='list'"> {{ item.description }} </h4>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
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
.container:hover {
  cursor: pointer;
}

.container.thumbnail:hover {
  background-color: #505050;
  border-radius: 8px;
}

.container.list:hover {
  background-color: #505050;
  border-radius: 8px;
}

.container {
  width: 100%;
  height: 100%;
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
@media (min-width: 768px) {
  .item.list .content-wrapper {
    padding-top: 0;
    padding-left: 16px;
    padding-right: 32px;
    width: 100%;
  }
}

.content-wrapper {
  word-wrap: normal;
  overflow: hidden;
  overflow-wrap: break-word;
  text-overflow: clip;
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

.bookmark-icon {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1;
  width: 45px;
  height: 60px;
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

@media (max-width: 768px) {
  .container.thumbnail:hover {
    background-color: transparent;
    padding-top: 4%;
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
    width: 45px;
    height: 60px;
    opacity: 0.6;
    border-top-right-radius: 8px;
  }

  .container.list {
    width: 100%;
    height: 20%;
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
    border-radius: 8px
  }

  .container .thumbnail {
    width: 100%;
    height: 100%;
  }

  .item .list{
    width: 100%;
    height: 100%;
  }

  .item.thumbnail {
    width: 100%;
    height: 200px;
    display: flex;
    flex-direction: column;
    align-content: center;
    align-items: center;
    margin: 0;
    border-radius: 8px;
  }

  .item.list {
    height: auto;
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .item.list .image-wrapper {
    height: 150px;
    width: 150px;
  }
}
</style>