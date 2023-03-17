import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import piniaPluginPersistedState from "pinia-plugin-persistedstate";
import './assets/main.css'

const pinia = createPinia();
const app = createApp(App)
pinia.use(piniaPluginPersistedState);
app.use(pinia).use(router).mount("#app");

export { pinia };
