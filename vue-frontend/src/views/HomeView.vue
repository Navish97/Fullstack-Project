<template>
  <div class="container">
    <div class="title">
      <h1>MyMarketPlace</h1>
    </div>
    <div class="wave-container">
      <Waves />
    </div>
  </div>
  <div class="wrapper">
    <div class="grid-container">
      <div class="listing-type">
        <FilterComponent />
        <ListingTypeButton />
      </div>
      <div class="items">
        <ItemList :items="itemStore.items" :listingType="currentListingType" :currentPage="currentPage" :totalPages="totalPages" @pageup="pageUp" @pagedown="pageDown" />
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
import Waves from '@/components/Wave/Wave.vue'

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

.page-count{
  color: black;
  font-weight: bolder;
  font-size: 120%;
  vertical-align: center;
  text-align: center;
}

.arrow {
  background-color: transparent;
  border: 0;
  width: 3%;
  height: 10%;
}

.arrow :hover {
  transform: scale(1.1);
}

.wave-container{
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
}

.title{
  font-weight: 300;
  margin-top: 5rem;
  color: white !important;
  font-size: 4rem;
  display: flex;
  justify-content: center;
  width: 100%;
}

.title h1{
  font-weight: 300;
  font-size: 4rem;
  display: flex;
  justify-content: center;
  text-align: center;
}

.container {
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  position: relative;
  overflow: hidden;
  height: 25rem;
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
  .title {
    margin-top: 2rem;
  }

  .title h1 {
    font-size: 2.5rem;
  }

  .grid-container {
    display: grid;
    grid-template-columns: 1fr;
    width: 100%;
    justify-content: center;
    padding: 0;
  }

  .listing-type {
    width: 100%;
  }

  .items {
    width: 100%;
  }
}
</style>
