<template>
  <v-avatar size="60" :color="getColorSelect.value">
    <span class="white--text font-name-size">{{ name }}</span>
  </v-avatar>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      name: "Patiphon",
      // colorSelect: { color: "Brown", value: "brown" },
    };
  },
  mounted() {
    this.name = this.name.substring(0, 2).toUpperCase();
  },
  computed: {
    ...mapGetters("account", ["getColorSelect"]),
  },
};
</script>

<style lang="scss" scoped>
.font-name-size {
  font-size: 24px;
  font-weight: 400;
}
</style>