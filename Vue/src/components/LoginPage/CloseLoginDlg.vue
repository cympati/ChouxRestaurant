<template>
  <v-card-actions class="backgroud-close-tab pa-0 pr-4 pt-4">
    <v-spacer></v-spacer>
    <div>
      <v-btn @click="close" class="ma-0" text icon color="red ">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
  </v-card-actions>
</template>

<script>
export default {
  methods: {
    async close() {
      await this.$emit("resetForm");
      this.setDialogLogin(false);
    },
  },
  props: {
    setDialogLogin: Function,
  },
};
</script>

<style lang="scss" scoped>
.backgroud-close-tab {
  background-color: white;
  height: 50px;
}
</style>