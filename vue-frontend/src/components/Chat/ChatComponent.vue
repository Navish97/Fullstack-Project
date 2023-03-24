<template>
    <div class="parent">
        <div class="messages">
            <MessageComponent v-for="message in messages" :message="message" :key="message.id" :class="{'messages sent': message.sent, 'messages received': !message.sent}" />
        </div>
        <div class="message-bar">
            <input type = "text" placeholder="Send a message..." v-model="messageInput">
            <button class="send-button" @click="sendMessageService()">Send</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, ref } from 'vue';
import { useMessageStore } from '@/stores/Message';
import { Message } from '@/types/MessageType';
import MessageComponent from './MessageComponent.vue';
import { sendMessage } from '@/service/MessagesService';
import { Chat } from '@/types/ChatType';
import { useChatStore } from '@/stores/Chat';

const messageStore = useMessageStore();
const chatStore = useChatStore();

const messageInput=ref('');

const props = defineProps({
    messages: {
        type: Array as () => Message[],
        required:true
    },
    chatId: {
  type: Number,
  default: 0,
  required: true,
},
});

async function sendMessageService(){
  const chat : Chat | undefined = chatStore.findChatById(props.chatId);
  if(chat !== undefined){
    await sendMessage(messageInput.value, props.chatId, chat.item)
    .then((response) => {
        console.log(response);
        messageStore.addMessage(response.data);
    })
    .catch((error) => {
        console.log('Error occured while sending message', error.message);
        messageInput.value = "Could not send message. Something went wrong";
    })
  }
}




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
