<template>
    <div class="parent">
      <div class = "details">
        <ChatDetails :chat="chat" />
      </div>
      <div class="message-wrapper">
        <div class="messages">
            <MessageComponent v-for="message in messages" :message="message" :key="message.id" :class="{'messages sent': message.sent, 'messages received': !message.sent}" />
        </div>
      </div>
        <div class="message-bar">
            <input type = "text" placeholder="Send a message..." v-model="messageInput">
            <button class="send-button" @click="sendMessageService()">Send</button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, ref, defineEmits } from 'vue';
import { useMessageStore } from '@/stores/Message';
import type { Message } from '@/types/MessageType';
import MessageComponent from './MessageComponent.vue';
import { sendMessage } from '@/service/MessagesService';
import type { Chat } from '@/types/ChatType';
import { useChatStore } from '@/stores/Chat';
import ChatDetails from './ChatDetails.vue';

const messageStore = useMessageStore();
const chatStore = useChatStore();

const messageInput=ref('');

const props = defineProps({
    messages: {
        type: Array as () => Message[],
        required:true
    },
    chat: {
      type: Object as () => Chat,
      required:true
    }


});

const emit = defineEmits(['chatIdUpdated']);


async function sendMessageService(){
  const chat : Chat | undefined = chatStore.findChatById(props.chat.id);
  if(chat !== undefined){
    await sendMessage(messageInput.value, props.chat.id, chat.item.id)
    .then((response) => {
        console.log(response);
        messageStore.addMessage(response.data.message);
        chat.id = response.data.chatId;
        emit('chatIdUpdated', chat.id);
    })
    .catch((error) => {
        console.log('Error occured while sending message', error.message);
        messageInput.value = "Could not send message. Something went wrong";
    })
  }
}




</script>

<style scoped>
.details {
  height: 12%;
  width: auto;
  position: sticky;
  display: flex;
  z-index: 1;
}
.message-wrapper{
  overflow-y: auto;
  height: 100%;
  scrollbar-width: none;
}
.message-wrapper::-webkit-scrollbar {
  display: none;
}

/* Hide scrollbar for IE, Edge and Firefox */
.example {
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
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
  position: relative;
}

.sent {
  align-items: end;
}

</style>
