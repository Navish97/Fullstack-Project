<script setup lang="ts">
import { RouterView } from 'vue-router'
import Navbar from './components/Header/AppHeader.vue'

import { useUserStore } from './stores/User'
import {onMounted} from "vue";

const userStore = useUserStore();

onMounted(async () => {
  await userStore.checkAuthStatus();
});


</script>

<template>
  <Navbar id="nav" />
  <RouterView />
</template>

<style scoped>

</style>
