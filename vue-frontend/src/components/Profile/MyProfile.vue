<template>
  <div>
    <h1>My Profile</h1>
    <div>
      <p>Name: {{ user.name }}</p>
      <p>Email: {{ user.email }}</p>
    </div>
    <button @click="handleLogOut">Log Out</button>
  </div>
</template>

<script setup lang="ts">
import { getUserData } from '@/service/Authentication/AuthenticationService';
import type { User } from "@/types/UserType";
import router from '@/router/index';
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/User';
import axiosInstance from "@/service/AxiosInstance";

const userStore = useUserStore();

const user = ref({} as User);

onMounted(() => {
  loadData();
});

async function logOut() {
  try{
    await axiosInstance.post('/api/auth/logout');
    userStore.logOut();
  }
  catch (error) {
    console.error(error);
  }
}

async function handleLogOut() {
  await logOut();
}

async function loadData() {
  try {
    const response = await getUserData();
    if (response) {
      user.value = response;
    } else {
      await userStore.logOut();
    }
  } catch (error) {
    console.error(error);
  }
}

</script>
