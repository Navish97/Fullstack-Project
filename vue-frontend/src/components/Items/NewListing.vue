<template>
  <AllCategories></AllCategories>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import type { Item } from '@/types/ItemType';
import { useItemStore } from '@/stores/Item';
import AllCategories from "@/components/Items/AllCategories.vue";





</script>

<style scoped>

</style>