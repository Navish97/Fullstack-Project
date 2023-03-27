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

<style scoped>
.wrapper {
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
}
</style>