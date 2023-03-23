<template>
    <div class ="chat-container">
        <div class="receive-label"> {{ chat.receiver }}</div>
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
.chat-container{
    background-color:cornflowerblue;
    color: rgba(30, 29, 29, 0.99);
    padding: 30px;
    border-bottom: 1px;
    border-style: solid;
    border-top: 0;
    border-right: 0;
    border-left: 0;
}
</style>