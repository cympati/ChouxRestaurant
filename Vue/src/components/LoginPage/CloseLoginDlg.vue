<template>
  <v-card-actions class="backgroud-close-tab pa-0 pr-4 pt-4">
    <v-spacer></v-spacer>
    <div>
      <v-btn @click="setDialogLogin(false)" class="ma-0" text icon color="red ">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
  </v-card-actions>
</template>

<script>
import { mapActions } from "vuex";

export default {
  methods: {
    ...mapActions("account", ["setDialogLogin"]),
  },
};
</script>

<style lang="scss" scoped>
.backgroud-close-tab {
  background-color: white;
  height: 50px;
}
</style>