<template>
  <div>
    <VNavAbove :page="page" />
    <slot></slot>
  </div>
</template>

<script>
export default {
  components: {
    VNavAbove: () => import("./VNavAbove"),
  },
  props: {
    page: String,
  },
};
</script>

<style>
</style>