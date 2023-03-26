<template>
  <div class="wrapper">
    <div id="header">
      <h1>Manage categories</h1>
    </div>
    <div class="categories-container">
      <div class="categories-wrapper">
        <div class="categories-grid">
          <div v-for="category in categories" :key="category.id" class="category">
            <div class="icon-container">
              <font-awesome-icon :icon="category.iconUrl" />
            </div>
            <div class="text-container">
              <span>{{ category.type }}</span>
            </div>
            <div class="action-container">
              <font-awesome-icon icon="plus" @click="addCategory(category.id)" />
              <font-awesome-icon icon="trash-alt" @click="showDeleteConfirm(category.id)" />
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
import { Modal } from 'ant-design-vue';
interface Category {
  id: number;
  type: string;
  iconUrl: string;
}


const deleteCategory = async (categoryId: number) => {
  try {
    const response = await axiosInstance.delete(`/api/categories/${categoryId}`);
    console.log(response.data);

    const index = categories.value.findIndex((category) => category.id === categoryId);
    if (index !== -1) {
      categories.value.splice(index, 1); // remove the category from the categories array
      console.log(categories.value);
    }
  } catch (error) {
    console.error(error);
  }
};

const showDeleteConfirm = (categoryId: number) => {
  const result = window.confirm("Are you sure you want to delete this category?");
  if (result) {
    deleteCategory(categoryId);
  }
}


const addCategory = (categoryId: number) => {
  // Implement adding category logic here
};

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
  color: white;
}

.categories-wrapper {
  display: flex;
  justify-content: center;
  font-size: 2rem;
}

.categories-grid {
  display: grid;
  /*grid-template-columns: 1fr 3fr;*/
  gap: 1rem;
}

.category {
  display: flex;
  align-items: center;
  font-weight: bold;
  cursor: pointer;
}

.icon-container {
  width: 60px;
  text-align: center;
}

.text-container {
  width: 100%;
}

.category > .text-container > span {
  margin-left: 1rem;
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

@media screen and (max-width: 768px) {
  .wrapper {
    width: 100%;
  }
  .categories-container {
    width: 100%;
  }
  .categories-wrapper {
    font-size: 2rem;
  }
  .category > .text-container > span {
    margin-left: 0.5rem;
  }
  .icon-container {
    width: 40px;
  }
  #header {
    width: 80%;
    font-size: 20px;
    padding-bottom: 20px;
  }
}
</style>
