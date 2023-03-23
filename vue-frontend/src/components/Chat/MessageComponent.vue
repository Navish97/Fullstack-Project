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
.message{
    background-color: cornflowerblue;
    width: fit-content;
    padding: 8px;
    border-radius: 10px;
    color: white;
    font-size: larger;
}
</style>