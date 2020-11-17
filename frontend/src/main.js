import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import routes from './routes'
import store from './store'

import firebase from 'firebase'
import StarRating from 'vue-star-rating'
var firebaseConfig = {
  apiKey: "AIzaSyAJTdTUoFX7M5kWARggtjzTqeBCdNjtXnI",
  authDomain: "my-food-652b5.firebaseapp.com",
  databaseURL: "https://my-food-652b5.firebaseio.com",
  projectId: "my-food-652b5",
  storageBucket: "my-food-652b5.appspot.com",
  messagingSenderId: "558386088891",
  appId: "1:558386088891:web:4dfe56049cb9e9fdea129e"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);



import 'es6-promise/auto'
Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(Vuex)

const router = new VueRouter({
  routes,
  mode: 'history',
});

// 메세지
router.beforeEach(
  (to, from, next) => {
    // console.log("next", store.state.user.userInfo)
    if ( store.state.user.userInfo ) {
      next( store.dispatch('user/getMessages'))
    } else {
      next()
    }
  }
)

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')

Vue.component('star-rating',StarRating);
export default router
