<template>
    <div class="wrapper">
      <div class="chat-wrapper">
        <ChatListComponent 
        :chats="chatStore.chats" 
        @chat-clicked="(chat) => loadChat(chat)"
        :selectedChatId="selectedChatId"/>
        <ChatComponent 
        :chat="getChat()" 
        @chat-id-updated="e => chatIdUpdated(e)" 
        :messages="messageStore.messages"
        v-if="getChat()" />
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import ChatListComponent from '@/components/Chat/ChatList.vue';
  import ChatComponent from '@/components/Chat/ChatComponent.vue';
  import { useChatStore } from '@/stores/Chat';
  import { useMessageStore } from '@/stores/Message';
  import { onMounted, ref } from 'vue';
  import { getChats, getMessages } from '@/service/MessagesService';
  import type { Chat } from '@/types/ChatType';

  const chatStore = useChatStore();
  const messageStore = useMessageStore();
  const selectedChatId = ref(-1);



  onMounted(() => {
    const tempChat : Chat | undefined = chatStore.findChatById(-1);
    getChats()
    .then((response) => {
      chatStore.setChats(response.data.chats)
      if(tempChat !== undefined){
        const existingChat : Chat | undefined = chatStore.findExistingChat(tempChat);
        if(existingChat !== undefined){
          loadChat(existingChat);
        }
        else{
          chatStore.newChat(tempChat);
          messageStore.setChatId(-1);
          selectedChatId.value = -1;
        }
      }
    })
  });

  function chatIdUpdated(chatId : number){
    selectedChatId.value = chatId;
    const chat : Chat | undefined = chatStore.findChatById(selectedChatId.value);
    if(chat !== undefined){
      loadChat(chat);
    }
  }

  function getChat() : Chat{
    const chat : Chat | undefined = chatStore.findChatById(selectedChatId.value);
    if(chat === undefined){
      //handle exceptions
    }
    return chat!;
  }

  function loadChat(chat : Chat){
    getMessages(chat)
    .then((response) => {
      messageStore.setMessages(response.data.messages);
      messageStore.setChatId(chat.id);
      selectedChatId.value = chat.id;
    })
  }


  </script>
  
  <style scoped>
  .wrapper {
    display: flex;
    justify-content:center;
    align-items: flex-start;
    height: 100vh;
    padding-top: 4rem /* add some padding to the outside */
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
  
  .chat-list-item.selected {
  background-color: #555;
}

  @media (max-width: 768px) {
    .wrapper {
      flex-direction: column;
    }

    .chat-wrapper {
      width: 100%;
      height: 100%;
      margin-bottom: 1rem;
    }
  }

  </style>