import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MyProfile from '../views/ProfileView.vue'
import RegisterView from '../views/RegisterView.vue'
import HomeView from '../views/HomeView.vue'
import ItemDetailsView from '../views/ItemDetailsView.vue'
import NewListingView from "../views/NewListingView.vue"
import { useUserStore } from '@/stores/User'
import MessagesView from '@/views/MessagesView.vue'
import EditItemView from '@/views/EditItemView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      beforeEnter: async (to, from, next) => {
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
      component: ItemDetailsView,
      props: true
    },
    {
      path: '/chats',
      name: 'chats',
      component: MessagesView,
    },
    {
      path: '/chats/new-chat:item',
      name: 'new-chat',
      component: MessagesView,
    },
    {
      path: '/item/edit-item:id',
      name: 'edit-item',
      component: EditItemView,
    },
  ]
})

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    await userStore.checkAuthStatus()
    if(to.meta.requiresAuth && !userStore.isLoggedIn) {
        next({name: 'login'});
    }
    else if(to.name === 'login' && userStore.isLoggedIn){
        next({name: 'home'});
    }
    else{
      next();
    }
});

export default router
