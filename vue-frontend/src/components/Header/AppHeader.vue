<template>
  <div id="wrapper">
    <nav id="navbar">
      <ul>
        <li><a href="/">Home</a></li>
        <li><a href="/new-listing">New listing</a></li>
        <li><a href="#">Contact</a></li>
        <li v-if="loggedIn">
          <RouterLink to="/my-profile">My Profile</RouterLink>
        </li>
        <li v-else>
          <RouterLink to="/login">Login</RouterLink>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup lang="ts">
import {useUserStore} from "@/stores/User";
import {computed} from "vue";

const userStore = useUserStore();

const loggedIn = computed(() => userStore.isLoggedIn());
</script>

<style scoped>
#wrapper{
  color: #fff;
  display: flex;
  justify-content: center;
  padding: 10px;
  z-index: 999;
  margin: auto;
  background-color: transparent;
}
#navbar {
  height: 3rem;
  background-color: transparent;
  width: 60%;
  display: flex;
  justify-content: center;
  justify-items: center;
  margin: auto;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999;
}

ul {
  display: flex;
  list-style: none;
  margin: auto 0;
  padding: 0;
  justify-content: center;
  width: 100%;
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