<template>
  <div class="container">
    <h1>Login</h1>
    <form @submit.prevent="login">
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
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'LoginView',
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
  }
});
</script>

<style scoped>
.container {
  max-width: 500px;
  margin: 0 auto;
  padding: 2rem 3rem;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

label {
  font-weight: bold;
}

input[type="email"],
input[type="password"] {
  padding: 0.6rem;
  border: 1px solid #ccc;
  border-radius: 0.25rem;
  font-size: 1rem;
  color: #e3e3e3;
  background-color: #575757;
  display: flex;
  flex-direction: column;
}

button {
  padding: 0.5rem;
  background-color: #cd570b;
  color: #fff;
  border: none;
  border-radius: 0.25rem;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background-color: rgba(199, 84, 10, 0.97);
}
</style>