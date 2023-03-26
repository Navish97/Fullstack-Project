<template>
    <div id="map">

    </div>
</template>

<script setup lang="ts">
import leaflet from 'leaflet';
import { onMounted, defineProps, watch } from 'vue';

const props = defineProps({
    latitude: {
        type :Number,
        default:60      
    },
    longitude: {
        type: Number,
        default: 10
    },
    maxDistance: {
        type: Number,
        default: 5000,
    },
    radiusOn: {
        type: Boolean,
        default: false
    },
    fixedMap: {
        type: Boolean,
        default: false
    }
    
})

const emit = defineEmits(['setLocation']);
onMounted(() => {
    let map = leaflet.map('map').setView([60, 10],3, {animate: false});
    leaflet.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);
    map.doubleClickZoom.disable();
    let marker = leaflet.marker([60,10]);
    let circle = leaflet.circle([60,10], {
        color: 'red',
        fillColor: '#f03',
        fillOpacity: 0.5,
        radius: 5000
    });
    if(props.fixedMap){
        map.dragging.disable();
        map.doubleClickZoom.enable();
        map.options.scrollWheelZoom = "center";
        map.setView([props.latitude,props.longitude],3);
        marker.setLatLng([props.latitude,props.longitude]);
    }
    toggleRadius(props.radiusOn);
    if(!props.fixedMap){
        map.on("click", (loc) => {
        map.setView(loc.latlng);
        if(props.radiusOn){
            circle.setLatLng(loc.latlng);
        }
        else{
            marker.setLatLng(loc.latlng);
        }
        emit('setLocation', loc.latlng.lat, loc.latlng.lng)
    })
    }
    watch(
    () => [props.latitude, props.longitude, props.maxDistance],
    ([newLat, newLng, maxDistance]) => {
      map.setView([newLat, newLng], map.getZoom());
      if(props.radiusOn){
        circle.setLatLng([newLat, newLng]);
        circle.setRadius(maxDistance);
      }
      else{
        marker.setLatLng([newLat, newLng]);
      }
    }
  );
  function toggleRadius(bool:boolean){
    if(bool){
        marker.removeFrom(map);
        circle.addTo(map);
    }
    else{
        circle.removeFrom(map);
        marker.addTo(map);
    }
}
});


</script>

<style scoped>
    #map{
        height: 180px;
        width:auto;
        margin-top: 5%;
    }
</style>