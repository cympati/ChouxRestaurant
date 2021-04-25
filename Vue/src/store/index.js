import Vue from "vue";
import Vuex from "vuex";
import VueCookies from "vue-cookies";
// import createPersistedState from "vuex-persistedstate";
// import SecureLS from "secure-ls";
// var ls = new SecureLS({ isCompression: false });

import state from "./state";
import * as getters from "./getters";
import * as mutations from "./mutations";
import * as actions from "./actions";

import account from "./modules/account";
import profile from "./modules/profile";
import reserve from "./modules/reserve";

Vue.use(Vuex, VueCookies);

export default new Vuex.Store({
  // plugins: [
  //   createPersistedState({
  //     storage: {
  //       getItem: (key) => ls.get(key),
  //       setItem: (key, value) => ls.set(key, value),
  //       removeItem: (key) => ls.remove(key),
  //     },
  //   }),
  // ],
  // plugins: [
  //   createPersistedState({
  //     paths: [],
  //     storage: {
  //       getItem: (key) => Vue.$cookies.get(key),
  //       setItem: (key, value) => Vue.$cookies.set(key, value),
  //       removeItem: (key) => Vue.$cookies.remove(key),
  //     },
  //   }),
  // ],
  state,
  // state have to changing
  getters,
  // change value or do something or create method
  mutations,
  // calls mutation
  actions,
  modules: { account, profile, reserve },
});
