<template>
    <div class = "message-wrapper">
        <div class="from">
            {{ message.senderId }}
        </div>
        <div class="message">
            {{ message.message }}
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import type { Message } from '@/types/MessageType';

const props = defineProps({
    message: {
        type: Object as () => Message,
        required: true
    }
});
</script>

<style>
    .message-wrapper{
        background-color: blue;
    }
</style>