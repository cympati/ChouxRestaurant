import Vue from "vue";
import Vuex from "vuex";
import VueCookies from "vue-cookies";

import account from "./modules/account";
import reserve from "./modules/reserve";

Vue.use(Vuex, VueCookies);

export default new Vuex.Store({
  state: {},
  // state have to changing
  getters: {},
  // change value or do something or create method
  mutations: {},
  // calls mutation
  actions: {},
  modules: { account, reserve },
});
