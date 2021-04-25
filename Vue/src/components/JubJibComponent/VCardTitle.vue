<template>
  <v-card-title :class="pd">
    <span class="headline">{{ cardTitle }}</span>
    <slot></slot>
  </v-card-title>
</template>

<script>
export default {
  data() {
    return {};
  },
  mounted() {},
  props: {
    pd: String,
    cardTitle: String,
  },
};
</script>

<style>
</style>