<template>
  <div class="wrapper">
    <div id="header">
      <h1>Manage categories</h1>
    </div>
    <div class="content">
      <div class="left-column">
        <div class="categories-container">
            <div class="categories-grid">
              <div v-for="category in categories" :key="category.id" class="category">
                <div class="icon-container">
                  <font-awesome-icon :icon="category.icon_url" />
                </div>
                <div class="text-container">
                  <span>{{ category.type }}</span>
                </div>
                <div class="action-container">
                  <font-awesome-icon icon="trash-alt" @click="showDeleteConfirm(category.id)" />
                </div>
              </div>
            </div>
        </div>
      </div>
      <div class="right-column">
        <div id="add-category-form">
          <BaseInput label="Category name" v-model="newCategoryType" placeholder="Enter category name" />
          <IconPicker @select="selectIcon" />
          <button id="addCategoryBtn" @click="addCategory">Add Category</button>
          <button style="margin-top: 50px" id="addCategoryBtn" @click="router.back()">Back</button>
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
import IconPicker from "@/components/Icon/IconPicker.vue";
import BaseInput from "@/components/Form/BaseInput.vue";
interface Category {
  id: number;
  type: string;
  icon_url: string;
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


const categories = ref<Category[]>([]);

const itemStore = useItemStore();

const selectCategory = (categoryId: number) => {
  itemStore.setNewListingCategory(categoryId);
  router.push('/new-listing');
};

const newCategoryType = ref("");
const selectedIcon = ref("");

const selectIcon = (icon) => {
  selectedIcon.value = icon;
};

const addCategory = async () => {
  try {
    const response = await axiosInstance.post("/api/categories", {
      type: newCategoryType.value,
      iconUrl: selectedIcon.value,
    });

    const newCategory: Category = response.data;
    categories.value.push(newCategory);

    newCategoryType.value = "";
    selectedIcon.value = "";
  } catch (error) {
    console.error(error);
  }
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

#addCategoryBtn{
  width: 60%;
  padding: 6px;
  background-color: white;
  border: none;
  border-radius: 5px;
  color: black;
  font-weight: 300;
  cursor: pointer;
  margin: auto;
  font-size: 1.5rem;
}
.wrapper {
  width: 80%;
  margin: 95px auto;
  height: fit-content;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content {
  display: flex;
  gap: 20px;
  width: 100%;
  margin-top: 20px;
}

.categories-grid {
  display: grid;
  gap: 1rem;
  width: 100%;
  height: fit-content;
}

.category {
  display: flex;
  align-items: center;
  font-weight: bold;
  cursor: pointer;
  padding: 0.5rem 1rem;
  background-color: rgba(255, 255, 255, 0.25);
  border-radius: 5px;
  margin-bottom: 1rem;
  position: relative;
}

.action-container {
  font-size: 1.5rem;
  position: absolute;
  right: 1rem;
}

.left-column{
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.right-column {
  flex: 1;
}

.categories-container {
  overflow-y: auto;
  color: white;
  display: flex;
  justify-content: flex-end;
  font-size: 1.5rem;
  width: 80%;
}

.icon-container {
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.text-container {
  flex-grow: 1;
}

#add-category-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
  width: 50%;
  margin: auto;
}


input {
  width: 100%;
  padding: 6px;
  background-color: rgba(255, 255, 255, 0.1);
  border: none;
  border-radius: 5px;
  color: white;
}

@media screen and (max-width: 768px) {
  .wrapper {
    width: 100%;
  }
  .categories-container {
    width: 80%;
    margin: auto;
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
    text-align: center;
  }

  #add-category-form {
    width: 80%;
  }
  .content{
    flex-direction: column-reverse;
  }
}
</style>
