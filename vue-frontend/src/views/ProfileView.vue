<template>
  <div class="container">

    <div class="content-wrapper">
      <ProfileBox />
      <div class="grid">
        <InfoBox :icon="faUser" title="My account" content="View and edit my account details"/>
        <InfoBox :icon="faTag" title="My listings" content="View all my listings"/>
        <InfoBox :icon="faBookmark" title="My bookmarks" content="View all my bookmarks"/>

      </div>
    </div>
    <div class="wave-container">
      <Wave></Wave>
    </div>
  </div>


</template>



<script setup lang="ts">
import Wave from '@/components/Wave/Wave.vue'
import InfoBox from "@/components/Profile/InfoBox.vue";
import ProfileBox from "@/components/Profile/ProfileBox.vue";
import { faUser } from "@fortawesome/free-solid-svg-icons";
import { faTag } from "@fortawesome/free-solid-svg-icons";
import { faBookmark } from "@fortawesome/free-solid-svg-icons";


</script>

<style scoped>

.grid {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  padding: 20px;
  width: fit-content;
  margin: 50px auto 0;
  gap: 50px;
}

.content-wrapper{
  justify-content: center;
  display: flex;
  margin-top: 120px;
  flex-direction: column;
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
.container {
  height: 100vh;
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  position: relative;
  justify-content: center;
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

@media (max-width: 768px) {
  .grid {
    flex-direction: column;
    align-items: center;
    gap: 30px;
    margin: 0 auto 0;
  }

  .content-wrapper{
    margin: 60px auto;
  }

  .wave-container {
  }

  .container {
    height: fit-content;
  }
}
</style>
