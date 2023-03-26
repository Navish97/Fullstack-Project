<template>
  <div class="container">
    <div class="content-wrapper">
      <ProfileBox />
      <div class="grid">
        <InfoBox :icon="faUser" title="My account" content="View and edit my account details"/>
        <InfoBox :icon="faTag" title="My listings" @click="loadItems" content="View all my listings"/>
        <InfoBox :icon="faBookmark" title="My bookmarks" content="View all my bookmarks"/>
      </div>
    </div>
    <div class="wave-container">
      <Wave></Wave>
    </div>
    <div v-if="showItems" class="overlay" @click="closeItems">
      <div class="item-list-wrapper" @click.stop>
        <button @click="closeItems" class="close-btn">X</button>
        <ItemList
            :pages="pages"
            :items="itemStore.items"
            :listingType="'thumbnail'"
            :currentPage="currentPage"
            @load-page="(page) => setPage(page)"
            @nav-page="(direction) => callPage(direction)" />
      </div>
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
import ItemList from "@/components/Items/ItemList.vue";
import { ref, computed} from 'vue';
import {getMyItems} from '@/service/ItemService';
import {useItemStore} from "@/stores/Item";
import {useUserStore} from "@/stores/User";
const itemStore = useItemStore();
const userStore = useUserStore();

const currentPage = ref(1);
const totalPages = ref(1);
const showItems = ref(false);

const pages = computed(() => {
  const pageArray = [];
  for (let i = 1; i <= totalPages.value; i++) {
    pageArray.push(i);
  }
  return pageArray;
});

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
  loadItems();
}

function setPage(page : number){
  currentPage.value = page;
  loadItems();
}
async function loadItems() {
  showItems.value = true;

  await getMyItems(currentPage.value - 1, itemStore.pageSize)
      .then((response) => {
        itemStore.setLists(response.data.items);
        currentPage.value = response.data["current-page"] + 1;
        totalPages.value = response.data["total-pages"];
      })
      .catch((error) => {
        console.log(error);
      });
}

function closeItems() {
  showItems.value = false;
}
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 3;
}

.item-list-wrapper {
  padding-top: 1rem;
  width: 70%;
  height: 90%;
  background-color: white;
  position: absolute;
  display: flex;
  border-radius: 16px;
  overflow-y: auto;
}

.close-btn {
  position: absolute;
  top: 20px;
  right: 20px;
  font-size: 24px;
  z-index: 1000;
  font-weight: bold;
  color: #000000;
  background: transparent;
  border: none;
  cursor: pointer;
}
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

.item-list-wrapper::-webkit-scrollbar {
  width: 10px;
}

.item-list-wrapper::-webkit-scrollbar-track {
  background-color: #f1f1f1;
  border-radius: 10px;
}

.item-list-wrapper::-webkit-scrollbar-thumb {
  background-color: #888;
  border-radius: 10px;
}

.item-list-wrapper::-webkit-scrollbar-thumb:hover {
  background-color: #555;
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
  item-list {
    width: 100%;
  }
  .item-list-wrapper {
    padding-top: 0;
    width: 95%;
    height: 95%;
    background-color: white;
    border-radius: 10px;
    position: absolute;
  }

  .close-btn {
    color: white;
    position:fixed;
    top: 5px;
    right: 5px;
  }
  .grid {
    padding-top: 2rem;
    flex-direction: column;
    align-items: center;
    gap: 30px;
    margin: 0 auto 0;
  }
  .overlay {
    width: 100%;
    height: 100%;
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
