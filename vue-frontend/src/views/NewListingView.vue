<template>
  <div id="wrapper">
    <AllCategories v-if="!hasChosenCategory"></AllCategories>
    <NewListing v-else></NewListing>

  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import type { Item } from '@/types/ItemType';
import { useItemStore } from '@/stores/Item';
import AllCategories from "@/components/Items/AllCategories.vue";
import NewListing from "@/components/Items/NewListing.vue";
import IconPicker from "@/components/Icon/IconPicker.vue";

const itemStore = useItemStore();

const chosenCategory = computed(() => itemStore.getNewListingCategory);
const hasChosenCategory = computed(() => chosenCategory.value !== 0);
</script>

<style scoped>

#wrapper{
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  height: 100vh;
}
</style>
