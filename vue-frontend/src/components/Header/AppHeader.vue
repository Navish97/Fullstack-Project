<template>
<nav>
<ul>
  <li><a href="/">Home</a></li>
  <li><a href="#">About</a></li>
  <li><a href="#">Contact</a></li>
  <div>
    <li><RouterLink to="/my-profile" v-if="loggedIn">My Profile</RouterLink></li>
    <li><RouterLink to="/login" v-if="!loggedIn">Login</RouterLink></li>
  </div>

</ul>
</nav>
</template>

<script setup lang="ts">
import {useUserStore} from "@/stores/User";
import {computed} from "vue";

const userStore = useUserStore();

const loggedIn = computed(() => {
  return userStore.isLoggedIn();
});
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