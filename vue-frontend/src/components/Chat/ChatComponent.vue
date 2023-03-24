<template>
    <div class="parent">
        <div class="messages">
            <MessageComponent v-for="message in messages" :message="message" :key="message.id" :class="{'messages sent': message.sent, 'messages received': !message.sent}" />
        </div>
        <div class="message-bar">
            <input type = "text" placeholder="Send a message...">
            <button class="send-button">Send</button>
        </div>
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
.messages {
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.message-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 5px;
}

.message-bar input {
  flex: 1;
  font-size: 15px;
  padding: 15px;
  border-radius: 5px;
  margin-right: 10px;
}

.send-button {
  background-color: cornflowerblue;
  color: #fff;
  font-size: 15px;
  padding: 15px;
  border-radius: 5px;
}

.parent {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  width: 100%;
  height: auto;
}

.sent {
  align-items: end;
}

</style>
