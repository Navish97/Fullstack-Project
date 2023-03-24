<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppHeader from './components/Header/AppHeader.vue'

import { useUserStore } from './stores/User'
import {onMounted} from "vue";

const userStore = useUserStore();

onMounted(async () => {
  await userStore.checkAuthStatus();
});

</script>

<template>
  <AppHeader></AppHeader>
  <RouterView />
</template>

<style scoped>

</style>
