<template>
    <div class="wrapper">
      <div class="chat-wrapper">
        <ChatListComponent :chats="chatStore.chats" @chat-clicked="(chat) => loadChat(chat)"/>
        <ChatComponent :messages="messageStore.messages" />
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import ChatListComponent from '@/components/Chat/ChatList.vue';
  import ChatComponent from '@/components/Chat/ChatComponent.vue';
  import { useChatStore } from '@/stores/Chat';
  import { useMessageStore } from '@/stores/Message';
  import { onMounted } from 'vue';
  import { getChats, getMessages } from '@/service/MessagesService';
  import type { Chat } from '@/types/ChatType';

  const chatStore = useChatStore();
  const messageStore = useMessageStore();

  onMounted(() => {
    getChats()
    .then((response) => {
      chatStore.setChats(response.data.chats)
    })
  });

  function loadChat(chat : Chat){
    getMessages(chat)
    .then((response) => {
      messageStore.setMessages(response.data.messages);
      messageStore.setChatId(chat.id);
    })
  }

  </script>
  
  <style>
  .wrapper {
    display: flex;
    justify-content:center;
    align-items: flex-start;
    height: 100vh;
    padding: 20px; /* add some padding to the outside */
  }
  
  .chat-wrapper {
    width: 80%;
    height: 100%;
    background-color: rgba(30, 29, 29, 0.99);
    padding: 20px; /* add some padding to the inside */
    border-radius: 15px;
    display: flex;
    flex-direction: row;
  }

  </style>