<template>
  <div class="box">
    <div class="inner">
      <div class="header-container">
        <h1>{{ title }}</h1>
        <font-awesome-icon class="icon" :icon="icon" />
      </div>
      <p>{{ content }}</p>
    </div>
  </div>
</template>


<script setup lang="ts">

import type {IconDefinition} from "@fortawesome/free-solid-svg-icons";

const props = defineProps<{
  title: string;
  content: string;
  icon: IconDefinition;
}>();


</script>

<style scoped>
p{
  font-size: 1.2rem;
  font-weight: 400;
  color: #000000;
  margin-top: 10px;

}
.box {
  width: 350px !important;
  height: 150px !important;
  background-color: white;
  border-radius: 20px;
  margin-right: 0;
}

.inner {
  margin: 20px;
  height: calc(100% - 40px);
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.icon {
  margin-right: 10px;
  font-size: 3rem;
  padding-top: 5px;
}

.box:hover {
  background-color: #efefef;
  cursor: pointer;
  transform: scale(1.05);
  transition: transform .5s ease-out;
}
</style>
