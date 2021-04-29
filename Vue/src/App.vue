<template>
  <v-app id="app">
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import Vue from "vue";
import VueScrollTo from "vue-scrollto";
import VueCookies from "vue-cookies";
import { mapActions } from "vuex";

Vue.use(VueCookies);
Vue.use(VueScrollTo, {
  container: "body",
  duration: 500,
  easing: "ease",
  offset: 0,
  force: true,
  cancelable: true,
  onStart: false,
  onDone: false,
  onCancel: false,
  x: false,
  y: true,
});

export default {
  name: "App",
  data() {
    return {};
  },
  methods: {
    ...mapActions("account", ["loadDataFromToken", "logout"]),
  },
  mounted() {
    // cookie
    Vue.$cookies.config("1d"); // expire time
    if (Vue.$cookies.get("token")) {
      this.loadDataFromToken();
    } else {
      this.logout();
    }
  },
  computed: {},
};
</script>

<style lang="scss" >
@import url("https://fonts.googleapis.com/css2?family=Mitr:wght@200;300;400;500;600;700&display=swap");
* {
  font-family: "Mitr", sans-serif;
  margin: 0;
  padding: 0;
}
</style>
