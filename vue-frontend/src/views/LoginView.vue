<template>
  <div class="container">
    <div class="content-wrapper">
    <h1>Login</h1>
      <form>
        <div class="input">
          <label for="email">Email:</label>
          <input type="email" id="email" v-model="email" required>
        </div>
        <div class="input">
          <label for="password">Password:</label>
          <input type="password" id="password" v-model="password" required>
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const email = ref('');
const password = ref('');
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.content-wrapper {
  max-width: 500px;
  margin: 0 auto;
  padding: 32px 48px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

label {
  font-weight: bold;
}

input[type="email"],
input[type="password"] {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 16px;
  color: #e3e3e3;
  background-color: #575757;
  display: flex;
  flex-direction: column;
}

button {
  padding: 8px;
  background-color: #cd570b;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: rgba(199, 84, 10, 0.97);
}
</style>