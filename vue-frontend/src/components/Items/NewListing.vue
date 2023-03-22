<template>
  <div class="wrapper">
    <form @submit.prevent="sendForm" class="form">
      <h1>New Listing</h1>
      <h3>Current category: Sport</h3>
      <h4>Change category</h4>
      <BaseInput id="inpTitle" class="input-container" type="text" label="Title" v-model="form.title" />
      <BaseInput id="inpDescription" class="input-container" type="text" label="Description" v-model="form.description" />
      <div class="icon-container"><font-awesome-icon :icon="iconUrl" /></div>
      <BaseInput id="inpPrice" class="input-container" type="number" label="Price" v-model="form.price" />
      <BaseInput id="inpLongitude" class="input-container" type="text" label="Longitude" v-model="form.longitude" />
      <BaseInput id="inpLatitude" class="input-container" type="text" label="Latitude" v-model="form.latitude" />
      <BaseInput id="inpImageUrls" class="input-container" type="text" label="Image URLs" v-model="form.imageUrls" />
      <button id="button">Create Listing</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import BaseInput from "@/components/Form/BaseInput.vue";
import router from "@/router";
import {ref, computed, onMounted} from "vue";
import axiosInstance from "@/service/AxiosInstance";
import { useItemStore } from "@/stores/Item";

const itemStore = useItemStore();
let chosenCategory = computed(() => itemStore.getNewListingCategory);

const form = ref({
  title: "",
  briefDescription: "",
  description: "",
  price: "",
  longitude: "",
  latitude: "",
  imageUrls: "",
});

const sendForm = async () => {
  try {
    const response = await axiosInstance.post("/api/items", form.value); // Update the URL to match your API endpoint for creating a new listing
    if (response.status === 200) {
      form.value.title = "";
      form.value.briefDescription = "";
      form.value.description = "";
      form.value.price = "";
      form.value.longitude = "";
      form.value.latitude = "";
      form.value.imageUrls = "";
      router.push("/"); // Redirect to the desired page after successful listing creation
    }
  } catch (error: any) {
    alert(error.response); // TODO: Display a user-friendly error message
  }
};



const fetchIcon = async (chosenCategory: any) => {
  try {
    const response = await axiosInstance.get(`/api/${chosenCategory}/icon-url`);
    iconUrl = response.data;
  } catch (error) {
    console.error(error);
  }
};

let iconUrl;

onMounted(async () => {
  await fetchIcon(chosenCategory);
});
</script>

<!-- You can use the existing styles from the RegisterForm component -->
<style scoped>
.wrapper {

}
h1{
  color: white;
  text-align: center;
  font-size: 40px;
  font-weight: 300;
}

h3{
  color: white;
  text-align: center;
  font-size: 20px;
  font-weight: 300;
}

h4{
  color: white;
  text-align: center;
  font-size: 15px;
  font-weight: 300;
}

textarea{
  resize: none;
  margin-top: 20px;
}
.form {
  margin: 50px auto 0 auto;
  width: 40%;
  height: fit-content;
  background-image: linear-gradient(to bottom right, rgb(92, 88, 88), rgb(37, 33, 33));
  padding: 40px;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}


.input-container {
  margin-top: 40px;
  height: 50px;
  position: relative;
  width: 100%;
  margin-bottom: 20px;
}

.input{
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

.cut{
  background-color: rgba(92, 88, 88,0);
  border-radius: 10px;
  height: 20px;
  left: 20px;
  position: absolute;
  top: -20px;
  transform: translateY(0);
  transition: transform 200ms;
  width: 76px;
}

.input:focus ~ .cut,
.input:not(:placeholder-shown) ~ .cut {
  transform: translateY(8px);
  background-color: rgba(92, 88, 88,1);
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

.input:focus ~ .placeholder,
.input:not(:placeholder-shown) ~ .placeholder {
  transform: translateY(-30px) translateX(10px) scale(0.75);
}

.input:not(:placeholder-shown) ~ .placeholder {
  color: #808097;
}

.input:focus ~ .placeholder {
  color: white;
}

#button {
  background: linear-gradient(45deg, #FC466B, #3F5EFB);
  border: 0;
  border-radius: 12px;
  color: white;
  cursor: pointer;
  font-size: 18px;
  height: 50px;
  margin-top: 40px;
  outline: 0;
  width: 100%;
  transition: transform 200ms;
}

#button:hover {
  transform: scale(1.05);
}

#button:disabled{
  cursor: initial;
  transform: scale(1);
}
/* Popup container - can be anything you want */
.popup {
  position: relative;
  display: inline-block;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  left: 50%;
}

/* The actual popup */
.popup .popuptext {
  visibility: hidden;
  width: 160px;
  background-color: #555;
  color: #fff;
  text-align: center;
  border-radius: 6px;
  padding: 8px 0;
  position: absolute;
  z-index: 1;
  bottom: 125%;
  left: 50%;
  margin-left: -80px;
  margin-bottom: -35px;
  cursor: default;
}

/* Popup arrow */
.popup .popuptext::after {
  content: "";
  position: absolute;
  top: 100%;
  left: 50%;
  margin-left: -5px;
  border-width: 5px;
  border-style: solid;
  border-color: #555 transparent transparent transparent;
}

/* Toggle this class - hide and show the popup */
.popup .show {
  visibility: visible;
  -webkit-animation: fadeIn 1s;
  animation: fadeIn 1s;
}

/* Add animation (fade in the popup) */
@-webkit-keyframes fadeIn {
  from {opacity: 0;}
  to {opacity: 1;}
}

@keyframes fadeIn {
  from {opacity: 0;}
  to {opacity:1 ;}
}

.text{
  color: white;
  font-size: 15px;
}

.link{
  color: white;
  font-size: 15px;
}

.link:hover{
  color: white;
  font-size: 15px;
  text-decoration: underline;
}

</style>