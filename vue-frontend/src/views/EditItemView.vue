<template>
    <div class ="wrapper">
        <EditItem :item="itemStore.getCurrentItem" />
    </div>
</template>

<script setup lang ="ts">
import EditItem from '@/components/Items/EditItem.vue';
import { useItemStore } from '@/stores/Item';
import { onMounted, defineProps } from 'vue';
import { getItemDetails } from '@/service/ItemService';


const itemStore = useItemStore();
</script>

<style>
</style>