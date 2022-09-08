import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [createPersistedState()],
  state: {
    unseenMenus:[],
    userState: null,
    isAuthenticated: false
  },
  getters: {
    isAuthenticated: (state) => {
      return state.isAuthenticated;
    },
    userState: (state) => {
      return state.userState;
    },
    unseenMenus:(state)=>{
      return state.unseenMenus;
    }

  },
  mutations: {
    setUnseenMenus(state,unseenMenus){
      state.unseenMenus=unseenMenus;
    },
    setUser(state,userState){
      state.userState=userState;
    },
    setIsAuthenticated(state, loggedIn){
      state.isAuthenticated=loggedIn;
    }
  },
  actions: {
    getUnseenMenus(context,unseenMenus){
      context.commit('setUnseenMenus',unseenMenus);
    },
    login(context,userState){
      context.commit('setUser',userState);
      context.commit('setIsAuthenticated',true);
    },
    logout(context){
      context.commit('setUser',null);
      context.commit('unseenMenus',[]);
      context.commit('setIsAuthenticated',false);
    }
  },
  modules: {
  }
})
