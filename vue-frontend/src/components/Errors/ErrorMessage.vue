<template>
  <div v-if="hasError" class="error-message">
    {{ message }}
    <button @click="$emit('clear-error')">X</button>
  </div>
</template>

<script lang="ts">
import {computed, ref} from "vue";
export default {
  name: "ErrorMessage",
  props: {
    message: {
      type: String,
      required: true,
    },
  },
  setup(props: any) {

    const hasError = computed(() => {
      return props.message !== "";
    });

    return {
      hasError
    };
  },
};
</script>

<style scoped>
.error-message {
  position: fixed;
  top: 75px;
  left: 50%;
  transform: translateX(-50%);
  background-color: #f44336;
  color: white;
  padding: 10px;
  border-radius: 5px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 300px;
}

.error-message button {
  background-color: transparent;
  border: none;
  color: white;
  font-size: 16px;
  cursor: pointer;
}
</style>
