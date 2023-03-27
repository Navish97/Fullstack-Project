<template>
  <div class="icon-picker">
    <BaseInput type="text" v-model="search" label="Search icons..." />
    <div v-if="props.selectedCategory" class="chosen-category">
      <h3>Chosen Category:</h3>
      <font-awesome-icon :icon="props.selectedCategory" />
    </div>
    <div v-else class="icon-grid">
      <div
          v-for="(icon, index) in filteredIcons"
          :key="index"
          class="icon"
          @click="$emit('select', icon)"
      >
        <font-awesome-icon id="icon" :icon="icon" />
      </div>
    </div>
  </div>
</template>



<script setup lang="ts">
import { computed, ref, watch } from "vue";
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from "@fortawesome/free-solid-svg-icons";
import BaseInput from "@/components/Form/BaseInput.vue";

const search = ref("");
const regularIcons = Object.values(far);
const solidIcons = Object.values(fas);
const icons = [...solidIcons, ...regularIcons];

const props = defineProps({
  selectedCategory: String,
});

watch(
    () => props.selectedCategory,
    (newVal, oldVal) => {
      if (newVal !== oldVal) {
        search.value = ""; // Clear the search input when the selected category changes
      }
    }
);

const filteredIcons = computed(() => {
  if (search.value === "") {
    return []; // Show no icons when the search bar is empty
  }

  const searchValue = search.value.trim().toLowerCase();
  const searchTerms = searchValue.split(/\s+/);

  return icons.filter(icon =>
      searchTerms.every(term => icon.iconName.toLowerCase().includes(term))
  );
});
</script>

<style scoped>
.icon-picker {
  width: 100%;
}

input {
  width: 100%;
  padding: 6px;
  margin-bottom: 8px;
}

.icon-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(40px, 1fr));
  gap: 8px;
  border: solid 3px white;
  border-radius: 15px;
  padding: 20px;
}

.icon {
  text-align: center;
  cursor: pointer;
}

#icon{
  font-size: 2.5rem;
}
</style>
