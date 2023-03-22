<template>
  <div class="items" v-if="selectedItem">
    <ItemDetails :item="itemStore.getCurrentItem"/>
  </div>
</template>

<script setup lang = "ts">
import ItemDetails from '@/components/Items/ItemDetails.vue';
import { useItemStore } from '@/stores/Item';
import {computed, defineProps, onMounted, ref} from "vue";
import {getItemDetails} from "@/service/ItemService";
import {useUserStore} from "@/stores/User";

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})

const itemStore = useItemStore();

onMounted(async () => {
  try {
    const response = await getItemDetails(props.id);
    itemStore.setCurrentItem(itemStore.responseToItem(response.data.item));
    itemStore.setBookmarked(response.data.isBookmarked);
  } catch (error) {
    console.error(error)
  }
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