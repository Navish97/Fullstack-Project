<template>
  <div id="wrapper">
    <RegisterForm id="register-form"></RegisterForm>
  </div>

</template>

<script setup lang="ts">
import RegisterForm from '@/components/Form/RegisterForm.vue'


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

#register-form{
  margin-top: 150px;
}


</style>
