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
        <button class="apply" @click = "sendQuery()">Apply</button>
    </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import router from '@/router';

const filterState = computed(() => {
    const query: {[key: string]: string} = {};
    if(minPrice.value !== null) {
        query.minPrice = minPrice.value.toString();
    }
    if(maxPrice.value !== null) {
        query.maxPrice = maxPrice.value.toString();
    }
    query.usedValue = usedBox.value.toString();
    query.newValue = newBox.value.toString();
    return query;
})
function sendQuery(){
    router.push({
        path:'/',
        query: filterState.value,
    })
}

onMounted(() => {
    const queryParams = new URLSearchParams(window.location.search);
    if(queryParams.has("minPrice") && queryParams.get("minPrice") !== ""){
        minPrice.value = parseInt(queryParams.get("minPrice")!);
    }
    if(queryParams.has("maxPrice") && queryParams.get("maxPrice") !== ""){
        maxPrice.value = parseInt(queryParams.get("maxPrice")!);
    }
    if(queryParams.has("newValue")){
        usedBox.value = JSON.parse(queryParams.get("usedValue")!);
    }
    if(queryParams.has("oldValue")){
        usedBox.value = JSON.parse(queryParams.get("usedValue")!);
    }

})

    const minPrice = ref<number | null>(null);
    const maxPrice = ref<number | null>(null);
    const usedBox = ref<boolean>(true);
    const newBox = ref<boolean>(true);

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