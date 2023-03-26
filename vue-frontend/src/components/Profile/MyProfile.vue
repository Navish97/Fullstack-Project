<template>
  <div class="profile-container">
    <div class="user-details">
      <h1 class="user-name">{{ user.name }}</h1>
      <p class="user-email">{{ user.email }}</p>
    </div>
    <div class="profile-picture-container">
      <img src="https://i.imgur.com/8Km9tLL.png" alt="profile" class="profile-picture" />
    </div>
<!--    <button @click="handleLogOut" class="log-out-btn">Log Out</button>-->
  </div>
</template>


<script setup lang="ts">
import { getUserData } from '@/service/Authentication/AuthenticationService';
import type { User } from "@/types/UserType";
import router from '@/router/index';
import { onMounted, ref } from 'vue';
import { useUserStore } from '@/stores/User';
import axiosInstance from "@/service/AxiosInstance";
import ProfileView from "@/views/ProfileView.vue";

const userStore = useUserStore();

const user = ref({} as User);

onMounted(() => {
  loadData();
});


async function loadData() {
  try {
    const response = await getUserData();
    if (response) {
      user.value = response;
    } else {
      await userStore.logOut();
    }
  } catch (error) {
    console.error(error);
  }
}

</script>

<style scoped>

.user-email{
  font-size: 1.4rem;
  color: white;
  font-weight: 300;
  margin-top: -25px;
}

.profile-picture-container{
  display: flex;
  justify-content: right;
}

.profile-container {
  display: grid;
  grid-template-columns: auto 1fr;
  column-gap: 1rem;
  justify-items: end;
  justify-content: space-evenly;
  height: 100%;
  align-items: center;
}

.user-details {
  grid-row: 1;
  grid-column: 1;
}

.user-name {
  font-size: 6rem;
  color: white;
  font-weight: 300;
  height: fit-content;
}

.profile-box {
  grid-row: 1;
  grid-column: 2;
  justify-self: end;
}

.profile-picture {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
}

.log-out-btn {
  font-size: 1.25rem;
  color: black;
  background-color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 20px;
  align-self: flex-end;
  text-align: left;
  justify-self: start;
}

.log-out-btn:hover {
  background-color: lightgray;
}

p {
  color: white;
}

@media (max-width: 768px) {
  .profile-picture{
    width: 100px;
    height: 100px;
  }

  .profile-container{
    height: 150px;
  }

  .profile-picture-container{
    justify-content: center !important;
  }


}
</style>
