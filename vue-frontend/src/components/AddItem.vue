<template>
  <div>
    <select v-model="selectedCategory">
      <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.name }}</option>
    </select>
    <div v-if="selectedCategory">
      <h2>{{ selectedCategory.name }}</h2>
      <ItemList :items="filteredItems"></ItemList>
    </div>
  </div>
</template>


<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from '@/store'
import ItemList from '@/components/Items/ItemList.vue'

const store = useStore()
const router = useRouter()

const categories = computed(() => store.state.categories)
const selectedCategory = ref(null)

const filteredItems = computed(() => {
  if (!selectedCategory.value) {
    return store.state.items
  } else {
    return store.state.items.filter(item => item.categoryId === selectedCategory.value)
  }
})

function navigateToCategory(categoryId: number) {
  router.push(`/category/${categoryId}`)
}

</script>