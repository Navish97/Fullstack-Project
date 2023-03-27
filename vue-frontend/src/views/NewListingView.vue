<template>
  <div id="wrapper1">
    <AllCategories v-if="!hasChosenCategory"></AllCategories>
    <div id="wrapper2" v-else>
      <NewListing></NewListing>
    </div>
    <button class="btn" v-if="isAdmin && !hasChosenCategory" @click="addCategory">Add Category</button>
  </div>
</template>

<script setup lang="ts">
import { defineProps, computed } from 'vue';
import type { Item } from '@/types/ItemType';
import { useItemStore } from '@/stores/Item';
import AllCategories from "@/components/Items/Category/AllCategories.vue";
import NewListing from "@/components/Items/NewListing.vue";
import IconPicker from "@/components/Icon/IconPicker.vue";
import { useUserStore } from '@/stores/User';
import router from '@/router/index'

const itemStore = useItemStore();

const chosenCategory = computed(() => itemStore.getNewListingCategory);
const hasChosenCategory = computed(() => chosenCategory.value !== 0);
const isAdmin = computed(() => useUserStore().getRole === "ADMIN");

function addCategory(){
  router.push('/new-listing/new-category');
}
</script>

<style scoped>

#wrapper1{
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
  min-height: 100vh;
}

#wrapper2{
  background: linear-gradient(-45deg, #cc63f1, #e73c7e, #23a6d5, #23d5ab);
}

.btn{
  position: absolute;
  bottom: 0;
  right: 0;
  margin: 20px;
  padding: 10px;
  background-color: #23d5ab;
  border: none;
  border-radius: 5px;
  color: white;
  font-size: 1.2rem;
  cursor: pointer;
}
</style>
