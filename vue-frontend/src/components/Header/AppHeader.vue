<template>
<nav>
<ul>
  <li><a href="/">Home</a></li>
  <li><a href="#">About</a></li>
  <li><a href="#">Contact</a></li>
  <li><RouterLink to="/login">Login</RouterLink></li>
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
}

li {
  margin-right: 50px;
}

a {
  color: #fff;
  text-decoration: none;
}
</style>