<template>
<nav>
<ul>
  <li><a href="/">Home</a></li>
  <li><a href="#">About</a></li>
  <li><a href="#">Contact</a></li>
  <li><RouterLink to="/my-profile">My Profile</RouterLink></li>
</ul>
</nav>
</template>

<script setup lang="ts">
</script>

<style>
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