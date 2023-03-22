<template>
  <div class="container" v-if="isLoggedIn" >
    <button class="bookmark-button" :class="{ 'bookmarked': isBookmarked }" @click="toggleBookmark">
      <span class="bookmark-icon"></span>
      <span class="bookmark-text">{{ isBookmarked ? 'Remove Bookmark' : 'Add Bookmark' }}</span>
    </button>
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import {useItemStore} from "@/stores/Item";
import {useUserStore} from "@/stores/User";
import {addBookmark, deleteBookmark} from "@/service/BookmarkService";

const itemStore = useItemStore();
const userStore = useUserStore();

async function toggleBookmark() {
  if (itemStore.isCurrentItemBookmarked) {
    const response = await deleteBookmark(itemStore.getCurrentItemId);
    if (response.status === 200) {
      itemStore.setCurrentItemBookmarked(false);
    }
  } else {
    const response = await addBookmark(itemStore.getCurrentItemId);
    if (response.status === 200) {
      itemStore.setCurrentItemBookmarked(true);
    }
  }
}

const isLoggedIn = computed(() => {
  return userStore.isLoggedIn();
});

const isBookmarked = computed(() => {
  return itemStore.isCurrentItemBookmarked;
});
</script>

<style scoped>
.container {
  padding-top: 8px;
}
.bookmark-button {
  display: inline-block;
  padding: 8px 8px;
  border-radius: 4px;
  border: 1px solid #000000;
  background-color: #1c1b1b;
  color: #FFFFFF;
  font-size: 16px;
  font-weight: bold;
  text-transform: uppercase;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.bookmark-button:hover {
  background: #4d4d4d;
}

.bookmark-button.bookmarked {
  color: #fafafa;
  background: #425432;
}

.bookmark-button.bookmarked:hover {
  background: #1a6e27;
}


.bookmark-text {
  display: inline-block;
  vertical-align: middle;
}
</style>