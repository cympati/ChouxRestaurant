<template>
  <!-- nav-above -->
  <v-app-bar
    app
    clipped-right
    color="white"
    height="100px"
    elevate-on-scroll
    class="px-6"
  >
    <h1 class="">{{ page }}</h1>
    <v-spacer></v-spacer>
    <router-link
      v-if="getIsLogin"
      :to="{
        name: 'Setting',
        params: { id: dataDetail.id },
      }"
      class="text-dec"
    >
      <VAvatar />
    </router-link>
    <div v-else></div>
  </v-app-bar>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "VNavAbove",
  data() {
    return {
      // profileDrawer: null,
      dataDetail: {
        firstName: "Patiphon",
        lastName: "Klangpraphan",
        id: "234",
        email: "chancreamz@gmail.com",
        password: "28052545",
        phoneNumber: "0956600463",
      },
    };
  },
  components: {
    VAvatar: () => import("../JubJibComponent/VAvatar"),
  },
  props: {
    page: String,
  },
  computed: {
    ...mapGetters("account", ["getIsLogin"]),
  },
};
</script>

<style lang="scss" scoped>
h1 {
  font-size: 3rem;
  letter-spacing: 0.2rem;
  font-weight: 600;
}

.text-dec {
  text-decoration: none;
  outline: none;
}
</style>