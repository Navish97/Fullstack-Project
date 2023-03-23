<template>
    <div class="convo-wrapper">
        <ChatPreview class = "chatelement" v-for="chat in chats" :chat="chat" :key="chat.chatid" />
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import type { Chat } from '@/types/ChatType';
import ChatPreview from '@/components/Chat/ChatPreview.vue';

const props = defineProps({
    chats: {
        type: Array as () => Chat[],
        required: true
    },
});

</script>

<style>
.convo-wrapper{
    display: flex;
    flex-direction: column;
    text-align: center;
    width: 25%;
    height: 100%;
    background-color: white;
    border-radius: 15px;
    overflow: hidden;
}

</style>