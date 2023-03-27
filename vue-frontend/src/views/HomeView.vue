<template>
  <div class="main-container">
    <div class="home-container">
      <div class="title">
        <h1>MyMarketPlace</h1>
      </div>
      <div class="wave-container" id="wave-container">
        <Waves />
      </div>
    </div>
    <div class="wrapper">
      <div class="grid-container">
        <div class="listing-type">
          <ListingTypeButton />
          <button class="filter-toggle" @click="toggleFilter">Toggle Filter</button>
          <div class="filter-container">
            <FilterComponent v-show="showFilter" @close="toggleFilter" />
          </div>
        </div>

        <div class="items" id="listing-items">
          <ItemList 
          :pages="pages" 
          :items="itemStore.items" 
          :listingType="currentListingType" 
           />
          <div class ="page-nav">
            <PaginationComponent 
            :pages="pages" 
            @load-page="(page) => setPage(page)" 
            @previous-page="(direction) => callPage(direction)"
            class = "pager"
            :current-page="currentPage"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import ItemList from '@/components/Items/ItemList.vue';
import ListingTypeButton from '@/components/ButtonChangeListingType.vue';
import FilterComponent from '@/components/Filter/FilterComponent.vue';
import {computed, onMounted, ref} from "vue";
import { useItemStore } from '@/stores/Item';
import { useUserStore } from '@/stores/User';
import { getItems } from '@/service/ItemService';
import { onBeforeRouteUpdate, useRoute } from 'vue-router';
import type {LocationQuery} from "vue-router";
import Waves from '@/components/Wave/Wave.vue'
import PaginationComponent from '@/components/Items/PaginationComponent.vue';

const itemStore = useItemStore();
const userStore = useUserStore();
const route = useRoute();

const currentListingType = computed(() => {
  return itemStore.currentListingType;
});

let currentPage = ref(1);
let totalPages = ref(1);

function scrollToTop() {
  const element = document.getElementById("wave-container");
  if(element) {
    element.scrollIntoView({ block: "start", behavior: "auto" });
  }
}

const pages = computed(() => {
  const pageArray = [];
  for (let i = 1; i <= totalPages.value; i++) {
    pageArray.push(i);
  }
  return pageArray;
});

const isDesktop = ref(window.innerWidth >= 769);
const showFilter = ref(isDesktop.value);

function toggleFilter() {
  if (!isDesktop.value) {
    showFilter.value = !showFilter.value;
  }
}

function setPage(page : number){
  scrollToTop();
  currentPage.value = page;
  loadItems(route.query);
}
function callPage(direction : number){
  if(direction > 0){
    if(currentPage.value < totalPages.value){
      currentPage.value = currentPage.value  +direction;
    }
  }
  else{
    if(currentPage.value > 1){
      currentPage.value = currentPage.value + direction;
    }
  }
  loadItems(route.query);
}
async function loadItems(route : LocationQuery){
    await getItems(currentPage.value-1, itemStore.pageSize, route)
    .then((response) => {
      itemStore.setLists(response.data.items);
      currentPage.value = response.data['current-page']+1;
      totalPages.value = response.data['total-pages'];
    })
    .catch((error) => {
      console.log(error);
    });

}
onBeforeRouteUpdate(async (to, from) => {
  console.log("route updated");
  loadItems(to.query);
})

onMounted(() => {
  loadItems(route.query);
  if (userStore.isLoggedIn) {
    userStore.fetchBookmarks();
  }
})

const emit = defineEmits(["load-page",'nav-page']);
function navigatePage(pageNav:number){
  emit('nav-page', pageNav);
}
function emitLoadPage(page:number){
  emit("load-page", page);
}


</script>

<style scoped>
  .items{
    width: 100%;
    flex: 1;
    min-width: 60%;
  }
@media (min-width: 769px) {
  .filter-toggle {
    display: none;
  }
}

.filter-toggle {
  background-color: #23d5ab;
  border: none;
  color: white;
  height: 100%;
  width: 10rem;
  border-radius: 8px;
  text-align: center;
  text-decoration: none;
  font-size: 16px;
  cursor: pointer;
}
.pager{
  padding-top: 1.5rem;
  padding-bottom: 3rem;
  display: flex;
  width: inherit;
  justify-content: center;
}


.listing-type {
  width: 250px;
  position: relative;
  overflow: hidden;
}

.main-container {
  display: grid;
  grid-template-columns: 1fr;
  width: 100%;
  height: 100%;
}

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

.arrow:hover {
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

.home-container {
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
  display: grid;
  justify-content: center;
  align-items: center;
  height: 100%;
  padding-top: 4rem;
}

.grid-container {
  display: grid;
  grid-template-columns: 250px auto;
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
  .title{
    padding-top: 1.5rem;
  }
  .home-container {
    height: 15rem;
  }
  .listing-type {
    display: grid;
    grid-template-columns: 1fr 1fr;

  }
  .filter-toggle {
    display: inline-block;
  }

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
    gap: 0;
    justify-content: center;
    padding: 0;
  }

  .listing-type {
    width: 100%;
  }

  .items {
    width: 100%;
    min-width: 0;
  }

  .wrapper {
    position: relative;
    top:0;
  }
}
</style>
