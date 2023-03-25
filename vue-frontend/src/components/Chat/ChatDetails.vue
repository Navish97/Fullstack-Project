<template>
  <div class="wrapper">
    <div class="detail-wrapper">
        <div class = "info-wrapper">
            <div class = "item-title"> {{ props.chat.item.title }}</div>
            <div class = "item-seller"> {{ props.chat.item.userName }}</div>
        </div>
    </div>
    <img :src="currentImage" alt="item-image">
  </div>
</template>

<script setup lang="ts">
import type { Chat } from '@/types/ChatType';
import { defineProps, computed } from 'vue';

const props = defineProps({
    chat: {
        type: Object as () => Chat,
        required:true,
    }
});
const currentImage = computed(() => {
  if (props.chat.item.images.length > 0) {
    return props.chat.item.images[0].data.toString();
  }
});
</script>

<style>

.wrapper{
  display: flex;
}

.detail-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding-left: 2rem;
  font-size: 24px;
  color: white;
}

img {
  max-width: 100%;
  height: auto;
  padding: 10px;
  margin-left: 30px;
}

.info-wrapper {
  width: 100%;
  display: grid;
  padding-left: 2rem;
  font-size: 24px;
}

.item-title {
  color: white;
}

.item-seller {
  color: white;
  font-weight: bold;

}
</style>