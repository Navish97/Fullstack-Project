<template>
  <div class="container" v-if="isLoggedIn">
    <button class="bookmark-button" :class="{ 'bookmarked': bookmarked }" @click="toggleBookmark">
      <span class="bookmark-icon"></span>
      <span class="bookmark-text">{{ bookmarked ? 'Bookmarked' : 'Bookmark' }}</span>
    </button>
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import {useItemStore} from "@/stores/Item";
import {useUserStore} from "@/stores/User";

const itemStore = useItemStore();
const userStore = useUserStore();

const isLoggedIn = computed(() => {
  return userStore.getLoggedInUserEmail;
});
const toggleBookmark = () => {
  return true;
};
const bookmarked = computed(() => {
  return userStore.isItemBookmarked(itemStore.getCurrentItem);
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



.bookmark-text {
  display: inline-block;
  vertical-align: middle;
}
</style>