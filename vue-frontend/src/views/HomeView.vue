<template>
  <div class="wave-wrapper">

    <div class="title">
      <h1>MyMarketPlace</h1>
    </div>
    <div>
      <svg class="waves" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
           viewBox="0 24 150 28" preserveAspectRatio="none" shape-rendering="auto">
        <defs>
          <path id="gentle-wave" d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z" />
        </defs>
        <g class="parallax">
          <use xlink:href="#gentle-wave" x="48" y="0" fill="rgba(255,255,255,0.7" />
          <use xlink:href="#gentle-wave" x="48" y="3" fill="rgba(255,255,255,0.5)" />
          <use xlink:href="#gentle-wave" x="48" y="5" fill="rgba(255,255,255,0.3)" />
          <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
        </g>
      </svg>
    </div>
  </div>
  <div class="wrapper">
    <div class="grid-container">
      <div class="listing-type">
        <FilterComponent />
      </div>
      <div class="items">
        <ItemList :items="itemStore.items" :listingType="currentListingType" :currentPage="currentPage" :totalPages="totalPages" @pageup="pageUp" @pagedown="pageDown" />
      </div>
      <div class="right-sidebar">
        <ListingTypeButton />
        <div>test</div>
      </div>
    </div>

  </div>
</template>



<script setup lang="ts">
import ItemList from '@/components/Items/ItemList.vue';
import ListingTypeButton from '@/components/ButtonChangeListingType.vue';
import FilterComponent from '@/components/FilterComponent.vue';
import {computed, onMounted} from "vue";
import { useItemStore } from '@/stores/Item';
import { useUserStore } from '@/stores/User';
import { getItems } from '@/service/ItemService';
import { onBeforeRouteUpdate, useRoute } from 'vue-router';
import {getUserBookmarks} from "@/service/BookmarkService";

const itemStore = useItemStore();
const userStore = useUserStore();
const route = useRoute();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});

let currentPage = 1;
let totalPages = 1;

function pageUp(){
  if(currentPage < totalPages){
    currentPage++;
    loadItems();
  }
}
function pageDown(){
  if(currentPage > 0){
    currentPage--;
    loadItems();
  }
}
async function loadItems(){
    await getItems(currentPage-1, 9, route.query)
    .then((response) => {
      itemStore.setLists(response.data.items);
      currentPage = response.data['current-page']+1;
      totalPages = response.data['total-pages'];
    })
    .catch((error) => {
      console.log(error);
    });
}
onBeforeRouteUpdate(async (to, from) => {
  console.log("route updated");
  getItems(currentPage, 9, to.query)
    .then((response) => {
      itemStore.setLists(response.data.items);
      currentPage = response.data['current-page']+1;
      totalPages = response.data['total-pages'];
    })
    .catch((error) => {
      console.log(error);
    });
})

onMounted(() => {
  loadItems();
  if (userStore.isLoggedIn()) {
    userStore.fetchBookmarks();
  }
})


</script>

<style scoped>

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
.wave-wrapper {
  height: 25rem;
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  position: relative;
  overflow: hidden;
}

.waves {
  position:relative;
  width: 100%;
  height:15vh;
  min-height:100px;
  max-height:150px;
  margin-top: 6.5rem;
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

.justify-content-center {
  display: flex;
  justify-content: center;
}
.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  padding-top: 4rem;
}

.grid-container {
  display: grid;
  grid-template-columns: auto 60% auto;
  grid-gap: 20px;
  justify-content: center;
  width: 80%;
}

.listing-type {
  width: 250px;
  padding: 20px;
}


.items {
  padding: 20px;
}

.right-sidebar {
  width: 250px;
  padding: 20px;
  background-color: white;
}

/* Animation */

.parallax > use {
  animation: move-forever 25s cubic-bezier(.55,.5,.45,.5)     infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
  animation-duration: 7s;
}
.parallax > use:nth-child(2) {
  animation-delay: -3s;
  animation-duration: 10s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 13s;
}
.parallax > use:nth-child(4) {
  animation-delay: -5s;
  animation-duration: 20s;
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
