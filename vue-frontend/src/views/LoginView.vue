<template>
  <div id="wrapper">
    <LoginForm id="loginForm"></LoginForm>
  </div>

</template>

<script setup lang="ts">
import LoginForm from '@/components/Form/LoginForm.vue'


</script>

<style scoped>
#wrapper{
  color: #fff;
  display: flex;
  justify-content: center;
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  height: 100vh;
  width: 100%;
  min-height: 500px;
}

#loginForm{
  margin-top: 150px;
  width: 100%;
  display: flex;
  justify-content: center;
}

@media (max-width: 768px) {
  #loginForm{
    margin: auto
  }
}


</style>
