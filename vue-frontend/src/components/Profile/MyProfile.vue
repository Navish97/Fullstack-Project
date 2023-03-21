<template>
  <div>
    <h1>My Profile</h1>
    <div>
      <p>Name: {{ user.name }}</p>
      <p>Email: {{ user.email }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { getUserData } from '@/service/Authentication/AuthenticationService';
import type { User } from "@/types/UserType";
import router from '@/router/index';
import { onMounted } from 'vue';


let user = {} as User;

onMounted(() => {
  loadData();
});

async function loadData() {
  try {
    const response = await getUserData();
    if (response) {
      user = response;
      console.log(response);
    } else {
      await router.push('/login');
    }
  } catch (error) {
    console.error(error);
  }
}

</script>
