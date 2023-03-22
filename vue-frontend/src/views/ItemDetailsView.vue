<template>
  <div class="items" v-if="selectedItem&&!isLoading">
    <ItemDetails :item="itemStore.getCurrentItem"/>
  </div>
</template>

<script setup lang = "ts">
import ItemDetails from '@/components/Items/ItemDetails.vue';
import { useItemStore } from '@/stores/Item';
import {computed, defineProps, onMounted, ref} from "vue";
import {getItemDetails} from "@/service/ItemService";

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})

const itemStore = useItemStore();
const isLoading = ref(true);
onMounted(async () => {
  isLoading.value = true;
  try {
    const response = await getItemDetails(props.id);
    itemStore.setCurrentItem(itemStore.responseToItem(response.data.item));
    itemStore.setCurrentItemBookmarked(response.data.isBookmarked);
  } catch (error) {
    console.error(error)
  }
  isLoading.value = false;
})

const selectedItem = computed(() => {
  if (itemStore.getCurrentItem !== undefined) {
    return itemStore.getCurrentItem;
  }
  else{
    return null;
  }
});
</script>

<style scoped>

</style>