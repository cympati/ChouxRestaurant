<template>
  <v-avatar size="60" :color="getColorSelect.value">
    <span class="white--text font-name-size">{{ getTwoChar }}</span>
  </v-avatar>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters("account", ["getColorSelect", "getTwoChar"]),
  },
};
</script>

<style lang="scss" scoped>
.font-name-size {
  font-size: 24px;
  font-weight: 400;
}
</style>