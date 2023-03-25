import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import piniaPluginPersistedState from "pinia-plugin-persistedstate";
import './assets/main.css'

/* import the fontawesome core */
import { library } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { fas } from '@fortawesome/free-solid-svg-icons';

/* import specific icons */
import { faShirt  } from '@fortawesome/free-solid-svg-icons'
import { faBook  } from '@fortawesome/free-solid-svg-icons'
import { faBolt  } from '@fortawesome/free-solid-svg-icons'
import { faCouch  } from '@fortawesome/free-solid-svg-icons'
import { faPuzzlePiece  } from '@fortawesome/free-solid-svg-icons'
import { faVolleyball  } from '@fortawesome/free-solid-svg-icons'
import { faTag } from '@fortawesome/free-solid-svg-icons'
import { faUser} from '@fortawesome/free-solid-svg-icons'


/* add icons to the library */
library.add(faShirt, faBook, faBolt, faCouch, faPuzzlePiece, faVolleyball, faTag, fas)

const pinia = createPinia();
const app = createApp(App).component('font-awesome-icon', FontAwesomeIcon)
pinia.use(piniaPluginPersistedState);
app.use(pinia).use(router).mount("#app");

export { pinia };
