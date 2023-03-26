<template>
  <div class="profile-wrapper" :style="{height: height + '%'}">
    <component :is="currentView"/>
    <button v-if="currentView === MyProfile" @click="handleLogOut" class="log-out-btn">Log Out</button>

  </div>
</template>


<script setup lang="ts">
import MyProfile from "@/components/Profile/MyProfile.vue";
import {useUserStore} from "@/stores/User";
import {onMounted, ref, computed} from "vue";
import type {User} from "@/types/UserType";
import axiosInstance from "@/service/AxiosInstance";
import {getUserData} from "@/service/Authentication/AuthenticationService";
import EditProfile from "@/components/Profile/EditProfile.vue";
import ChangePassword from "@/components/Profile/ChangePassword.vue";


const userStore = useUserStore();

const user = ref({} as User);

onMounted(() => {
  loadData();
});

const props = defineProps({
  height: {
    type: Number,
  }
})

const currentView = computed(() => {
  const path = window.location.pathname;
  if (path === "/my-profile/edit") {
    return EditProfile;
  }
  else if (path === "/my-profile/edit/change-password") {
    return ChangePassword;
  }
  else {
    return MyProfile;
  }
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
  /*height: 50%;*/
  flex-direction: column;
  align-items: flex-start;
  border: solid 3px white;
  border-radius: 20px;
  padding: 2rem;
  margin: auto;
  height: 100%;
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

@media (max-width: 768px) {
  .profile-wrapper{
    width: 100%;
    height: 40%;
  }

  .profile-wrapper{
    border: none;
  }

  .log-out-btn{
    position: relative;
    bottom: 0;
    left: 0;
    margin-top: 40px;
  }
}
</style>
