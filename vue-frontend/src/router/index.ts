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
import MyAccountView from "@/views/MyAccountView.vue"
import ChangePasswordView from "@/views/ChangePasswordView.vue"
import NewCategoryView from "@/views/NewCategoryView.vue"


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
      path: '/my-profile/edit',
      name: 'edit-profile',
      component: MyAccountView,
        meta: {
            requiresAuth: true
        }
    },
    {
      path: '/my-profile/edit/change-password',
      name: 'change-password',
      component: ChangePasswordView,
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
        meta: {
            requiresAuth: true
        }
    },
    {
      path: '/chats/new-chat:item',
      name: 'new-chat',
      component: MessagesView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/item/edit-item:id',
      name: 'edit-item',
      component: EditItemView,
        meta: {
            requiresAuth: true
        }
    },
    {
      path: '/new-listing/new-category',
      name: 'new-category',
      component: NewCategoryView,
      meta: {
        requiresAuth: true
      }
    },
  ]
})

router.beforeEach(async (to, from, next) => {
    await useUserStore().checkAuthStatus()
    if(to.meta.requiresAuth && !useUserStore().isLoggedIn) {
        next({name: 'login'});
    }
    else if (to.name === 'login' && useUserStore().isLoggedIn) {
      next({name: 'home'});
    }
    else{
      next();
    }
});

export default router
