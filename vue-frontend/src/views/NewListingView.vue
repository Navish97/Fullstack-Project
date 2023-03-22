<template>
  <AllCategories v-if="!hasChosenCategory"></AllCategories>
  <NewListing v-else></NewListing>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import type { Item } from '@/types/ItemType';
import { useItemStore } from '@/stores/Item';
import AllCategories from "@/components/Items/AllCategories.vue";
import NewListing from "@/components/Items/NewListing.vue";

const itemStore = useItemStore();

const chosenCategory = computed(() => itemStore.getNewListingCategory);
const hasChosenCategory = computed(() => chosenCategory.value !== 0);
</script>
