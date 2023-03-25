<template>
  <div class="icon-picker">
    <input type="text" v-model="search" placeholder="Search icons..." />
    <div class="icon-grid">
      <div
          v-for="(icon, index) in filteredIcons"
          :key="index"
          class="icon"
          @click="$emit('select', icon)"
      >
        <font-awesome-icon :icon="icon" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import { library } from "@fortawesome/fontawesome-svg-core";
import { far } from '@fortawesome/free-regular-svg-icons';

const search = ref("");
const icons = Object.values(far);

const filteredIcons = computed(() => {
  if (search.value === "") {
    return icons;
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
}

.icon {
  text-align: center;
  cursor: pointer;
}
</style>
