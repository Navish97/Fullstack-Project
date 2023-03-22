<template>
  <h1 style="text-align: center; font-size: 75px">Chosen category id: {{ chosenCategory }}</h1>
</template>

<script setup lang="ts">


import {useItemStore} from "@/stores/Item";
import {computed} from "vue";

const itemStore = useItemStore();

const chosenCategory = computed(() => itemStore.getNewListingCategory);





</script>

<style scoped>

</style>