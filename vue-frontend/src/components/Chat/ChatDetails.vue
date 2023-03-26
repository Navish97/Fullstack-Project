<template>
    <div class="detail-wrapper">
        <div class = "info-wrapper">
            <div class = "item-title"> {{ props.chat.item.title }}</div>
            <div class = "item-seller"> {{ props.chat.item.userName }}</div>
        </div>
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
})
</script>

<style scoped>

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