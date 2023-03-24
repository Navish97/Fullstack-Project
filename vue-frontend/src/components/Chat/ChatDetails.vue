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

<style>
</style>