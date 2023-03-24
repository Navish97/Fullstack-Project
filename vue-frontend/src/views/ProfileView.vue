<template>
  <div class="container">

    <div class="title">
      <h1>Hello there</h1>
    </div>
    <div class="profile-wrapper">
      <MyProfile></MyProfile>
    </div>
    <div class="wave-container">
      <Wave></Wave>
    </div>
  </div>


</template>



<script setup lang="ts">
import Wave from '@/components/Wave/Wave.vue'
import MyProfile from "@/components/Profile/MyProfile.vue";


</script>

<style scoped>

.profile-wrapper{
  display: flex;
  justify-content: center;
}

.wave-container{
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}

Wave {
  position: relative;
  width: 100%;
  height: 15vh;
  min-height: 100px;
  max-height: 150px;
}

.title{
  font-weight: 300;
  margin-top: 5rem;
  color: white !important;
  font-size: 4rem;
  display: flex;
  justify-content: center;
}

.title h1{
  font-weight: 300;
  font-size: 4rem;
  display: flex;
  justify-content: center;
}
.container {
  height: 100vh;
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  position: relative;
  overflow: hidden;
}


@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

/* Animation */

.parallax > use {
  animation: move-forever 25s cubic-bezier(.55,.5,.45,.5)     infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
  animation-duration: 10s;
}
.parallax > use:nth-child(2) {
  animation-delay: -3s;
  animation-duration: 13s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 16s;
}
.parallax > use:nth-child(4) {
  animation-delay: -5s;
  animation-duration: 23s;
}
@keyframes move-forever {
  0% {
    transform: translate3d(-90px,0,0);
  }
  100% {
    transform: translate3d(85px,0,0);
  }
}
</style>
