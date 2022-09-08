import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import AddFoodAdmin from '../views/AddFoodAdmin.vue'
import AddBreakfast from '../views/AddBreakfast.vue'
import AddLunch from '../views/AddLunch.vue'
import AddDinner from '../views/AddDinner.vue'
import AddMenu from '../views/AddMenu.vue'
import FoodAdmin from '../views/FoodAdmin.vue'
import ProfileDetails from '../views/ProfileDetails.vue'
import ChooseMenu from '../views/ChooseMenu.vue'
import ViewMenus from '../views/ViewMenus.vue'
import OwnMenu from '../views/OwnMenu.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/FoodAdmin',
    name: 'FoodAdmin',
    component: FoodAdmin
  },
  {
    path: '/addFoodAdmin',
    name: 'AddFoodAdmin',
    component: AddFoodAdmin
  },
  {
    path: '/addBreakfast',
    name: 'AddBreakfast',
    component: AddBreakfast
  },
  {
    path: '/addLunch',
    name: 'AddLunch',
    component: AddLunch
  },
  {
    path: '/addDinner',
    name: 'AddDinner',
    component: AddDinner
  },
  {
    path: '/addMenu',
    name: 'AddMenu',
    component: AddMenu
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/profile',
    name: 'ProfileDetails',
    component: ProfileDetails
  },
  {
    path: '/chooseMenu',
    name: 'ChooseMenu',
    component: ChooseMenu
  },
  {
    path: '/ownMenu',
    name: 'OwnMenu',
    component: OwnMenu
  },
  {
    path: '/viewMenus',
    name: 'ViewMenus',
    component: ViewMenus
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
