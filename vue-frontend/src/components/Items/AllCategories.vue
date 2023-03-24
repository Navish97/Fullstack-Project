<template>
  <div class="wrapper">
    <div id="header">
      <h1>What do you want to sell?</h1>
    </div>
    <div class="categories-container">
      <div class="categories-wrapper">
        <div class="categories-grid">
          <div v-for="category in categories" :key="category.id" class="category" @click="selectCategory(category.id)">
            <div class="icon-container">
              <font-awesome-icon :icon="category.iconUrl" />
            </div>
            <div class="text-container">
              <span>{{ category.type }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axiosInstance from '@/service/AxiosInstance';
import { useItemStore } from '@/stores/Item';
import router from '@/router/index';


import { computed } from 'vue';

interface Category {
  id: number;
  type: string;
  iconUrl: string;
}

const categories = ref<Category[]>([]);

const itemStore = useItemStore();

const selectCategory = (categoryId: number) => {
  itemStore.setNewListingCategory(categoryId);
  router.push('/new-listing');
};

onMounted(async () => {
  try {
    const response = await axiosInstance.get('/api/categories');
    categories.value = response.data;
    console.log(categories.value);

  } catch (error) {
    console.error(error);
  }
});

</script>

<style scoped>
.wrapper {
  width: 55%;
  margin: 95px auto;
  height: fit-content;
}
.categories-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%; /* Set the width to fill the viewport width */
  color: white;
}

.categories-wrapper {
  display: flex;
  justify-content: center;
  font-size: 2rem; /* Increase the font size for everything within the wrapper */
}

.categories-grid {
  display: grid;
//grid-template-columns: 1fr 3fr; /* create two columns, one for the icons and the other for the category names */
  gap: 1rem;
}

.category {
  display: flex;
  align-items: center;
  font-weight: bold;
  cursor: pointer;
}

.icon-container {
  width: 60px; /* Increase the fixed width for the icon container */
  text-align: center; /* Center the icon within the container */
}

.text-container {
  width: 100%; /* Set a fixed width for the text container */
}

.category > .text-container > span {
  margin-left: 1rem; /* add some space between the icon and the category name */
}

#header {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%;
  margin: auto;
  font-size: 30px;
  color: white;
}
</style>
