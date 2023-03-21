<template>
    <div class = "filter-wrapper">
        <div class = "header">Filter</div>
        <div class = "price">
            <label>Price</label>
            <div class = "price-input">
                <div>
                    <input type="number" id="min-price" name ="min-price" v-model="minPrice">
                    <label for="min-price">From</label>
                </div>
                <div>
                    <input type="number" id="max-price" name ="max-price" v-model="maxPrice">
                    <label for="max-price">To</label>
                </div>
            </div>
        </div>
        <div class = "condition">
            <div>Condition</div>
            <div class = "condition-state">
                <input type ="checkbox" id="used-box" v-model="usedBox">
                <label for="used-box">Used</label>
            </div>
            <div class = "condition-state">
                <input type ="checkbox" id="new-box" v-model="newBox">
                <label for="new-box">New</label>
            </div>
        </div>
        <button class="apply" @click = "applyFilters">Apply</button>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { getItems } from '@/service/ItemService';
import { useItemStore } from '@/stores/Item';

const itemStore = useItemStore();

async function loadPage(filter : object){
    await getItems(0, 15, filter)
    .then((response) => {
        itemStore.setLists(response.data.items);
    })
    .catch((error) => {
        console.log(error);
    });
}

    const minPrice = ref<number | null>(null);
    const maxPrice = ref<number | null>(null);
    const usedBox = ref<boolean>(true);
    const newBox = ref<boolean>(true);

    const applyFilters = () => {
        const minPriceValue = minPrice.value;
        const maxPriceValue = maxPrice.value;
        const usedValue = usedBox.value;
        const newValue = newBox.value;

        loadPage({
            minPrice:minPriceValue, maxPrice:maxPriceValue, usedValue:usedValue, newValue:newValue
        });
    };

</script>


<style scoped>
    .filter-wrapper{
        width: 200px;
        background-color: rgba(30, 29, 29, 0.99);
        color: #ffffff;
        padding: 12px 16px;
        border-radius: 4px;
        border: 1px solid #646464;
        font-size: 14px;
        cursor: pointer;
        text-align: left;
        align-content: center;
    }
    .price-input{
        display: inline-flex;
        margin: 3%;
    }
    .price-input input{
        width: 97%;
    }
    .header{
        text-align: center;
        font-weight: bolder;
    }
    .condition-state{
        padding: 5px;
        align-items: center;
    }
    .condition-state input{
        margin-right: 5px;
        vertical-align: middle;
    }
    .condition-state label{
        vertical-align: middle;
    }
    .apply {
        border-radius: 4px;
        border: 1px solid #646464;
        cursor: pointer;
        font-size: 14px;
        padding:5px 8px;
        margin:5px;
        margin-top: 0;
        margin-left: auto;
    }
    

</style>