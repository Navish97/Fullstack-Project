<template>
    <div class ="chat-container">
        <div class="receive-label"> {{ chat.userName }}</div>
    </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import type { Chat } from '@/types/ChatType';

const props = defineProps({
    chat: {
        type: Object as () => Chat,
        required: true
    },
});

</script>
<style>
.receive-label {
    font-size: 16px;
    font-weight: bold;
    text-align: center;
}
.chat-container{
    background-color:cornflowerblue;
    color: rgba(30, 29, 29, 0.99);
    padding: 20px;
    border-bottom: 1px;
    border-style: solid;
    border-top: 0;
    border-right: 0;
    border-left: 0;
}
</style>