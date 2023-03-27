<template>
  <div class="edit-container">
    <div class="user-details">
      <div class="header">Change password</div>
      <form @submit.prevent="updatePassword">
        <div class="form-group">
          <BaseInput id="inpOldPassword" class="input-container" type="password" label="Old Password" v-model="oldPassword"/>
        </div>
        <div class="form-group">
          <BaseInput id="inpNewPassword" class="input-container" type="password" label="New Password" v-model="newPassword"/>
        </div>
        <div>
          <button @click="route('/my-profile/edit')" class="update-btn" type="button">Cancel</button>
        </div>
        <div>
          <button id="changePasswordBtn" class="update-btn" type="submit">Change password</button>
        </div>
      </form>
    </div>
  </div>
</template>


<script setup lang="ts">
import { getUserData } from '@/service/Authentication/AuthenticationService';
import type { User } from "@/types/UserType";
import router from '@/router/index';
import { onMounted, ref, computed } from 'vue';
import { useUserStore } from '@/stores/User';
import axiosInstance from "@/service/AxiosInstance";
import ProfileView from "@/views/ProfileView.vue";
import BaseInput from "@/components/Form/BaseInput.vue";

const userStore = useUserStore();

const user = ref({} as User);

const oldPassword = ref('');
const newPassword = ref('');

const oldPasswordValid = computed(() => true);
const newPasswordValid = computed(() => true);

const formValid = computed(() => oldPasswordValid.value && newPasswordValid.value);

function route(route : string){
  router.push(route);
}

async function updatePassword() {
  if (!formValid.value) {
    return;
  }

  const passwordData = {
    oldPassword: oldPassword.value,
    newPassword: newPassword.value,
  };

  try {
    const response = await axiosInstance.post('/api/my-profile/change-password', passwordData);
    if (response.status === 200) {
      alert('Password changed successfully!')
      await router.push('/my-profile');
    }
    else if(response.status === 400){
      alert('Old password is incorrect!')
    }
    else if(response.status === 401){
      alert('You are not authorized to change password!')
    }
    else{
      alert('Something went wrong!')
    }
  }
    catch (error) {
    console.error(error);
  }
}
</script>

<style scoped>

.update-btn{
  flex: 1;
  font-size: 1.25rem;
  color: black;
  background-color: white;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius: 15px;
  margin-top: 20px;
  width: 50%;
}

@media (min-width: 768px) {
  button {
    width: auto;
  }
}

.header{
  font-size: 4rem;
  color: white;
  font-weight: 300;
  height: fit-content;
  margin-bottom: 20px;
}

.user-email{
  font-size: 1.4rem;
  color: white;
  font-weight: 300;
  margin-top: -25px;
}

.edit-container {
  display: flex;
}

.user-details {
  flex: 1;
}

.profile-picture-container{
  margin-left: 2rem;
}

.profile-container {
  display: grid;
  grid-template-columns: auto 1fr;
  align-items: center;
  column-gap: 1rem;
  justify-items: end;
  justify-content: end;
  height: 100%;
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

.input-container {
  margin-top: 40px;
  height: 50px;
  position: relative;
  width: 100%;
  margin-bottom: 20px;
}

.input {
  background-color: rgb(92, 88, 88);
  border-radius: 12px;
  border: 0;
  box-sizing: border-box;
  color: #eee;
  font-size: 18px;
  height: 100%;
  outline: 0;
  padding: 4px 20px 0;
  width: 100%;
}

.cut {
  background-color: rgba(92, 88, 88, 0);
  border-radius: 10px;
  height: 20px;
  left: 20px;
  position: absolute;
  top: -20px;
  transform: translateY(0);
  transition: transform 200ms;
  width: 45px;
}

.input:focus~.cut,
.input:not(:placeholder-shown)~.cut {
  transform: translateY(8px);
  background-color: rgba(92, 88, 88, 1);
}

.placeholder {
  color: white;
  font-family: sans-serif;
  left: 20px;
  line-height: 14px;
  pointer-events: none;
  position: absolute;
  transform-origin: 0 50%;
  transition: transform 200ms, color 200ms;
  top: 20px;
}

::placeholder {
  color: white;
}

.input:focus~.placeholder,
.input:not(:placeholder-shown)~.placeholder {
  transform: translateY(-30px) translateX(10px) scale(0.75);
}

.input:not(:placeholder-shown)~.placeholder {
  color: #808097;
}

.input:focus~.placeholder {
  color: white;
}

@media (max-width: 768px) {
  .header{
    font-size: 3rem;
  }
  .edit-container{
    width: 80%;
    margin: auto;
  }

  div, button{
    margin-top: 50px;
  }
}
</style>
