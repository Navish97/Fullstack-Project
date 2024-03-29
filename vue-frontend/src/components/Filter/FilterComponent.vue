<template>
    <div class = "filter-wrapper">
        <div class = "header">Filter</div>
      <button class="close-button" @click="emit('close')">X</button>
        <div class = "searchbar">
          <label for="search">Search</label>
          <textarea v-model="search" maxlength = "40" rows="2" id="search" placeholder="Search in title/description"></textarea>
        </div>
        <div class = "dual-input-wrapper">
            <label>Price</label>
            <div class = "dual-input">
                <div>
                  <label for="min-price">From</label>
                  <input type="number" id="min-price" name ="min-price" v-model="minPrice">
                </div>
                <div>
                  <label for="max-price">To</label>
                  <input type="number" id="max-price" name ="max-price" v-model="maxPrice">
                </div>
            </div>
        </div>
        <div class="category">
      <label>Category</label>
      <select v-model="selectedCategory">
        <option value="">All</option>
        <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.type }}</option>
      </select>
      </div>
      <div class="location-wrapper" id ="location">
        <label>Location</label>
        <div class ="dual-input">
          <div>
          <label for ="longitude">Longitude</label>
          <input type="number" id ="longitude" name="longitude" v-model="longitude">
        </div>
        <div>
          <label for = "latitude">Latitude</label>
          <input type="number" id = "latitude" name = "latitude" v-model ="latitude">
        </div>
        </div>
        <div>
          <label for ="maxDistance">Max distance (m)</label>
          <input type="number" id ="maxDistance" name="maxDistance" v-model="maxDistance">
        </div>
        <div>
        </div>
      </div>
      <div class="map-container" ref="mapContainer">
        <MapComponent id="map" :latitude="latitude!" :longitude="longitude!" :maxDistance="maxDistance!" :radiusOn="true" @setLocation="(lat, long) => setLocation(lat, long)" />
      </div>
      <button class="apply" @click = "sendQuery()">Apply</button>
      <button class="reset" @click="resetFilters()">Reset</button>
    </div>
</template>

<script setup lang="ts">
import {ref, computed, onMounted, defineEmits} from 'vue';
import router from '@/router';
import { useItemStore } from '@/stores/Item';
import  axiosInstance  from '@/service/AxiosInstance';
import type {Category} from "@/types/CategoryType";
import MapComponent from '../Map/MapComponent.vue';

const itemStore = useItemStore();
const emit = defineEmits(['close']);
const filterState = computed(() => {
    const query: {[key: string]: string} = {};
    if(minPrice.value !== null) {
        query.minPrice = minPrice.value.toString();
    }
    if(maxPrice.value !== null) {
        query.maxPrice = maxPrice.value.toString();
    }
    if (selectedCategory.value !== null) {
    query.category = selectedCategory.value.toString();
  }
    if (search.value !== ""){
      query.search = search.value!;
    }
    if (longitude.value !== null){
      query.longitude = longitude.value.toString();
    }
    if (latitude.value !== null) {
      query.latitude = latitude.value.toString();
    }
    if (maxDistance.value !== null) {
      query.maxDistance = (maxDistance.value/1000).toString();
    }
    
    return query;
})

function setLocation(newLatitude : number, newLongitude:number){
  latitude.value = newLatitude;
  longitude.value = newLongitude;
}
function sendQuery(){
    router.push({
        path:'/',
        query: filterState.value,
    }).then((response) => {emit('close');})
}

const zoom = 2;



function resetFilters() {
  minPrice.value = null;
  maxPrice.value = null;
  selectedCategory.value = null;
  search.value = "";
  longitude.value = null;
  latitude.value = null;
  maxDistance.value = null;
  sendQuery();
}

onMounted(async () => {
  try {
    const response = await axiosInstance.get('/api/categories');
    categories.value = response.data;

  } catch (error) {
    console.error(error);
  }
});

onMounted(() => {
    const queryParams = new URLSearchParams(window.location.search);
    if(queryParams.has("minPrice") && queryParams.get("minPrice") !== ""){
        minPrice.value = parseInt(queryParams.get("minPrice")!);
    }
    if(queryParams.has("maxPrice") && queryParams.get("maxPrice") !== ""){
        maxPrice.value = parseInt(queryParams.get("maxPrice")!);
    }
    if(queryParams.has("category") && queryParams.get("category") !== ""){
      selectedCategory.value = parseInt(queryParams.get("category")!);
    }
    if(queryParams.has("search") && queryParams.get("search") !== ""){
      search.value = queryParams.get("search")!;
    }
    if(queryParams.has("longitude") && queryParams.get("longitude") !== ""){
      longitude.value = parseInt(queryParams.get("longitude")!);
    }
    if(queryParams.has("latitude") && queryParams.get("latitude") !== ""){
      latitude.value = parseInt(queryParams.get("latitude")!);
    }
    if(queryParams.has("maxDistance") && queryParams.get("maxDistance") !== ""){
      latitude.value = parseInt(queryParams.get("maxDistance")!);
    }
})
    const minPrice = ref<number | null>(null);
    const maxPrice = ref<number | null>(null);
    const categories = ref<Category[]>([]);
    const selectedCategory = ref<number | null>(null);
    const search = ref<string>('');
    const longitude = ref<number | null>(10);
    const latitude = ref<number | null>(60);
    const maxDistance = ref<number |null>(5000);
</script>


<style scoped>
@media (min-width: 769px) {
  .close-button {
    display: none;
  }
}

.close-button {
  position: absolute;
  top: 5px;
  left: 5px;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  background-color: rgba(30, 29, 29, 0.99);
  color: #ffffff;
  border: none;
  font-size: 16px;
  cursor: pointer;
}

.reset {
  border-radius: 4px;
  border: 1px solid #646464;
  cursor: pointer;
  font-size: 14px;
  padding: 5px 8px;
  margin: 5px 5px 5px 0;
}
#search {
  padding: 3%;
  margin-top: 1%;
  resize: none;
  height: auto;
}
.searchbar {
  display: flex;
  flex-direction: column;
}

.filter-wrapper{
  width: 400px;
  background-color: #575656;
  color: white;
  padding: 12px 16px;
  border-radius: 15px;
  font-size: 14px;
  text-align: left;
  align-content: center;
}
label{
  color: white;
  font-size: 1rem;
}
.dual-input{
  display: inline-flex;
}
.dual-input input{
  width: 97%;
}
.header{
  text-align: center;
  font-weight: bolder;
  color: white;
  font-size: 2rem;
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
margin: 5px 5px 5px auto;
}
    .category {
  margin-top: 10px;
}

.category label {
  display: inline-block;
  margin-bottom: 5px;
}

.category select {
  display: block;
  width: 100%;
  padding: 5px;
  font-size: 14px;
  border-radius: 4px;
  border: 1px solid #646464;
  background-color: #ffffff;
  color: #333333;
  margin-bottom: 5%;
}

    @media (max-width: 768px) {
      .filter-wrapper {
        position: fixed;
        width: 100%;
        bottom: 0;
        left: 0;
        right: 0;
        z-index: 1000;
        background-color: white;
        box-shadow: 0 -2px 5px rgba(0, 0, 0, 0.15);
        transition: transform 1s ease;
      }
      #map{
        height: 200px;
        width: auto;
      }
    }

</style>