<template>
  <div class="navbar-container" :class="{ 'scrolled': isScrolled }">
    <div id="navbar" >
      <div id="hamburger" @click="toggleMenu">
        <span></span>
        <span></span>
        <span></span>
      </div>
      <ul :class="{ 'open': menuOpen}">
        <li @click="menuOpen = false"><a href="/">Home</a></li>
        <li @click="menuOpen = false"><a href="/new-listing">New listing</a></li>
        <li @click="menuOpen = false"><a href="/chats">Messages</a></li>
        <li v-if="loggedIn" @click="menuOpen = false">
          <RouterLink to="/my-profile">My Profile</RouterLink>
        </li>
        <li v-else @click="menuOpen = false">
          <RouterLink to="/login">Login</RouterLink>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useUserStore} from "@/stores/User";
import {computed, ref, onMounted} from "vue";

const userStore = useUserStore();
const loggedIn = computed(() => userStore.isLoggedIn());

const menuOpen = ref(false);
const toggleMenu = () => {
  menuOpen.value = !menuOpen.value;
};

const isScrolled = ref(false)

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
})

const handleScroll = () => {
  if (window.scrollY > 0) {
    isScrolled.value = true
  } else {
    isScrolled.value = false
  }
}
</script>

<style scoped>
#navbar {
  height: 3rem;
  background-color: transparent;
  width: 60%;
  display: flex;
  justify-content: center;
  justify-items: center;
  margin: auto;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 999;
  transition: background-color 2s ease;
}

.scrolled {
  background-color: rgba(28, 27, 27, 0.64) !important;
}

.navbar-container {
  position: fixed;
  width: 100%;
  background-color: rgba(28, 27, 27, 0.24);
  display: flex;
  justify-content: center;
  height: 3rem;
  z-index: 3;
  transition: background-color 2s ease;
}

#hamburger {
  display: none;
  cursor: pointer;
  flex-direction: column;
  justify-content: space-around;
  width: 2rem;
  height: 2rem;
  position: absolute;
  left: 1rem;
  top: 0.5rem;
}

#hamburger span {
  width: 100%;
  height: 0.25rem;
  background-color: #fff;
}

ul {
  display: flex;
  list-style: none;
  margin: auto 0;
  padding: 0;
  justify-content: center;
  width: 100%;
}

li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex: 1;
}

a {
  color: #fff;
  text-decoration: none;
  font-weight: 300;
  font-size: 1.2rem;

}

@media (max-width: 768px) {
  #navbar {
    background-color: rgba(0, 0, 0, 0.34);
    position: absolute;
    width: 100%;
  }

  #hamburger {
    display: flex;
  }

  ul {
    display: none;
    flex-direction: column;
    align-items: center;
    width: 100%;
    background-color: rgba(0, 0, 0, 0.34);
    position: absolute;
    top: 3rem;
    left: 0;
    right: 0;
    padding: 1rem 0;
  }

  ul.open {
    display: flex;
  }

  li {
    margin-bottom: 1rem;
  }
}
</style>