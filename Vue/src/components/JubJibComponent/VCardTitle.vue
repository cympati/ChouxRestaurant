<template>
  <v-card-title :class="pd">
    <span class="headline">{{ cardTitle }}</span>
    <slot></slot>
  </v-card-title>
</template>

<script>
export default {
  props: {
    pd: String,
    cardTitle: String,
  },
};
</script>
