<template>
  <div class="item-details" v-if="props.item.imageURLs">
    <div class="flex-container">
      <div class="image-container">
        <img :src="bookmark" alt="bookmarked" class="bookmark-icon" v-if="itemIsBookmarked"/>
        <img :src="currentImage" class="item-image" alt="item-image" />
        <button class="image-btn prev" @click="prevImage" v-if="props.item.imageURLs.length>1">&lt;</button>
        <button class="image-btn next" @click="nextImage" v-if="props.item.imageURLs.length>1">&gt;</button>
        <div class="image-index">{{ currentImageIndex + 1 }} / {{ props.item.imageURLs.length }}</div>
      </div>
      <div class="toolbar">
        <bookmark-component />
        <RouterLink to="/chats" v-if="isLoggedIn" @click="preLoadChat()" class="contact-button">Contact seller</RouterLink>
        <RouterLink v-else to="/login" class="contact-button">Contact seller</RouterLink>
      </div>
      <div class="item-info">
        <h2>{{ item.title }}</h2>
        <p>(DD): {{ item.latitude }}, {{ item.longitude }}</p>
        <p>Selger Id: {{ item.userId }}</p>
        <h3>Pris: {{ formattedPrice }}</h3>
        <h4>{{ item.description }}</h4>
        <br>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed, ref, watch } from 'vue';
import type { Item } from '@/types/ItemType';
import BookmarkComponent from "@/components/Items/BookmarkButton.vue";
import {useItemStore} from "@/stores/Item";
import bookmark from '@/assets/bookmark.png';
import {useUserStore} from "@/stores/User";
import { RouterLink } from 'vue-router';
import type { Chat } from '@/types/ChatType';
import { useChatStore } from '@/stores/Chat';

const itemStore = useItemStore();
const userStore = useUserStore();
const chatStore = useChatStore();

const isLoggedIn = computed(() => {
  return userStore.isLoggedIn();
});

const props = defineProps({
  item: {
    type: Object as () => Item,
    required: true
  },
});

watch(() => props.item, () => {
  currentImageIndex.value = 0;
});

function preLoadChat() {
  const newChat: Chat = {
        id: -1,
        userId:props.item.userId,
        userName:props.item.userName,
        userEmail:props.item.userEmail,
        item:props.item.id,
      }

  chatStore.newChat(newChat);
}

const currentImageIndex = ref(0);

const itemIsBookmarked = computed(() => {
  return itemStore.isCurrentItemBookmarked;
});

const currentImage = computed(() => {
  if (props.item !== undefined && props.item.imageURLs !== undefined) {
    return props.item.imageURLs[currentImageIndex.value];
  }
});

const formattedPrice = computed(() => {
  return `${props.item.price.toFixed(0)} Kr`;
});

function prevImage() {
  if (currentImageIndex.value > 0) {
    currentImageIndex.value -= 1;
  }
  else {
    currentImageIndex.value = props.item.imageURLs.length - 1;
  }
}

function nextImage() {
  if (currentImageIndex.value < props.item.imageURLs.length - 1) {
    currentImageIndex.value += 1;
  }
  else {
    currentImageIndex.value = 0;
  }
}
</script>

<style scoped>

.bookmark-icon {
  position: absolute;
  top: 0;
  right: 0;
  z-index: 1;
  width: 60px;
  height: 100px;
  opacity: 0.5;
  border-top-right-radius: 24px;
}

.flex-container {
  padding-top: 24px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.item-details {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  width: 100%;
  height: 100%;
}

.item-info {
  padding-top: 16px;
}

.image-container {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  width: 60vw;
  height: 80vh;
}

.image-index {
  position: absolute;
  object-fit: contain;
  bottom: 0;
  font-size: 18px;
  padding: 2px 16px 2px 16px;
  background-color: rgba(0, 0, 0, 0.37);
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.item-image {
  border-radius: 24px;
  width: 100%;
  height: 100%;
}

.image-btn {
  cursor: pointer;
  position: absolute;
  height:100%;
  width: 40px;
  background-color: rgba(0, 0, 0, 0.32);
  color: #fff;
  border: none;
  font-size: 50px;
}

.image-btn.next {
  border-top-right-radius: 24px;
  border-bottom-right-radius: 24px;
}

.image-btn.prev {
  border-top-left-radius: 24px;
  border-bottom-left-radius: 24px;
}
.prev {
  left: 0;
}
.next {
  right: 0;
}
.toolbar{
  display: flex;
  justify-content: space-between;
  width: 28%;
  padding-top: 8px;
}
.contact-button {
  display: inline-block;
  padding: 8px 8px;
  border-radius: 4px;
  border: 1px solid #000000;
  background-color: #1c1b1b;
  color: #FFFFFF;
  font-size: 16px;
  text-transform: uppercase;
  cursor: pointer;
  transition: background-color 0.3s ease;
  vertical-align: middle;
}

.contact-button:hover {
  background: #4d4d4d;
}
</style>
