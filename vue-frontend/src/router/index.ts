import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MyProfile from '../views/MyProfile.vue'
import RegisterView from '../views/RegisterView.vue'
import HomeView from '../views/HomeView.vue'
import FilterComponent from '@/components/FilterComponent.vue'
import ItemDetailsView from '../views/ItemDetailsView.vue'
import NewListingView from "../views/NewListingView.vue";
import { getItems } from '@/service/ItemService';


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      beforeEnter: async (to, from, next) => {
        const userStore = useUserStore();
        await userStore.checkAuthStatus();
        const { query } = to;
        getItems(0, 15, query);
        next();
      },
    },
    {
      path: '/new-listing',
      name: 'new-listing',
      component: NewListingView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/my-profile',
      name: 'my-profile',
      component: MyProfile,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/item/:id',
      name: 'item-details',
      component: ItemDetailsView
    },
  ]
})

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();

    if(to.meta.requiresAuth && !userStore.isLoggedIn()) {
        next({name: 'login'});
    }
    else{
      next();
    }
});

export default router
