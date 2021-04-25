<template>
  <div class="text-center">
    <v-menu rounded="xl" offset-y>
      <template v-slot:activator="{ on: menu, attrs }">
        <v-tooltip bottom>
          <template v-slot:activator="{ on: tooltip }">
            <v-avatar
              size="60"
              :color="getColorSelect.value"
              v-bind="attrs"
              v-on="{ ...tooltip, ...menu }"
            >
              <span class="white--text font-name-size">{{ name }}</span>
            </v-avatar>
          </template>
          <span>Click me</span>
        </v-tooltip>
      </template>
      <v-list>
        <v-list-item v-for="(item, index) in items" :key="index">
          <router-link
            to="/reservation"
            v-if="item.title === 'Reservation'"
            class="text-dec"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </router-link>
          <router-link
            :to="{
              name: 'Setting',
              params: { id: id },
            }"
            v-else-if="item.title === 'Setting'"
            class="text-dec"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </router-link>
          <!-- $store.dispatch('setLoginStatus', false) -->
          <div v-else-if="item.title === 'Logout'" @click="logout()">
            <router-link to="/" class="text-dec">
              <v-divider class="width-line"></v-divider>
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </router-link>
          </div>
        </v-list-item>
      </v-list>
    </v-menu>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      name: "Patiphon",
      id: "234",
      items: [
        { title: "Reservation" },
        { title: "Setting" },
        { title: "Logout" },
      ],
    };
  },
  mounted() {
    this.name = this.name.substring(0, 2).toUpperCase();
  },
  computed: {
    ...mapGetters("account", ["getColorSelect"]),
  },
  methods: {
    ...mapActions("account", ["logout"]),
  },
};
</script>

<style lang="scss" scoped>
.font-name-size {
  font-size: 24px;
  font-weight: 400;
}
.text-dec {
  text-decoration: none;
  color: #212123;
  &:hover {
    color: #6079d6;
  }
}
.width-line {
  width: 100px;
  margin-bottom: 15px;
}
</style>