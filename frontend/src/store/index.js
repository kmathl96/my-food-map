import Vue from 'vue'
import Vuex from 'vuex'
import user from './user' 
import nav from './nav' 
import review from './review' 


Vue.use(Vuex)

export default new Vuex.Store({
  modules:{
    user,
    nav,
    review,
  },
})
