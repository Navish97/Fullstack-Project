<template>
    <div class="parent">
        <div class="messages">
            <MessageComponent v-for="message in messages" :message="message" :key="message.id" />
        </div>
        <div class="message-bar"></div>
    </div>
</template>

<script setup lang="ts">
import { defineProps } from 'vue';
import { useMessageStore } from '@/stores/Message';
import { Message } from '@/types/MessageType';
import MessageComponent from './MessageComponent.vue';

const messageStore = useMessageStore();

const props = defineProps({
    messages: {
        type: Array as () => Message[],
        required:true
    },
});

</script>

<style>
</style>
