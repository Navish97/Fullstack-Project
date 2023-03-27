<template>
    <div class="convo-wrapper">
        <ChatPreview class = "chatelement" 
        v-for="chat in chats" 
        :chat="chat" 
        :key="chat.id"
        :class="{ selected: chat.id === selectedChatId }"
        @click="chatClicked(chat)" />
    </div>
</template>

<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import type { Chat } from '@/types/ChatType';
import ChatPreview from '@/components/Chat/ChatPreview.vue';

const props = defineProps({
    chats: {
        type: Array as () => Chat[],
        required: true
    },
    selectedChatId: {
        type: Number,
        default: null,
    }
});

const emit = defineEmits(["chat-clicked"]);
function chatClicked(chat : Chat) {
    emit('chat-clicked', chat);
}
</script>

<style scoped>
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
.chatelement.selected {
  background-color: #555;
  color: #fff;
  font-weight: bold;
}

</style>