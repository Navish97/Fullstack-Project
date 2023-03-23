<template>
  <nav>
    <ul>
      <li><a href="/">Home</a></li>
      <li><a href="/new-listing">New listing</a></li>
      <li><a href="#">Contact</a></li>
      <li v-if="loggedIn">
        <RouterLink to="/chats">Messages</RouterLink>
      </li>
      <li v-if="loggedIn">
        <RouterLink to="/my-profile">My Profile</RouterLink>
      </li>
      <li v-else>
        <RouterLink to="/login">Login</RouterLink>
      </li>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import {useUserStore} from "@/stores/User";
import {computed} from "vue";

const userStore = useUserStore();

const loggedIn = computed(() => userStore.isLoggedIn());
</script>

<style scoped>
nav {
  background-color: #333;
  color: #fff;
  display: flex;
  justify-content: center;
  padding: 10px;
  height: 50px;
}

ul {
  display: flex;
  list-style: none;
  margin: auto 0;
  padding: 0;
  justify-content: center;
  width: 50%;
}

li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1;
}

a {
  color: #fff;
  text-decoration: none;
}
</style>