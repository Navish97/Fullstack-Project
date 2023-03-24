<template>
  <div class="profile-wrapper">
    <MyProfile></MyProfile>
    <button @click="handleLogOut" class="log-out-btn">Log Out</button>
  </div>
</template>


<script setup lang="ts">
import MyProfile from "@/components/Profile/MyProfile.vue";
import {useUserStore} from "@/stores/User";
import {onMounted, ref} from "vue";
import {User} from "@/types/UserType";
import axiosInstance from "@/service/AxiosInstance";
import {getUserData} from "@/service/Authentication/AuthenticationService";


const userStore = useUserStore();

const user = ref({} as User);

onMounted(() => {
  loadData();
});

async function logOut() {
  try{
    await axiosInstance.post('/api/auth/logout');
    userStore.logOut();
  }
  catch (error) {
    console.error(error);
  }
}

async function handleLogOut() {
  await logOut();
}

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

.profile-wrapper{
  position: relative;
  width: 800px;
  height: 50%;
  flex-direction: column;
  align-items: flex-start;
  border: solid 3px white;
  border-radius: 20px;
  padding: 2rem;
  margin: auto;
}

.log-out-btn {
  position: absolute;
  bottom: -20px;
  left: 40px;
  font-size: 1.25rem;
  color: black;
  background-color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 20px;
  z-index: 1;
}
</style>
