<template>
  <div>
    <v-icon size="25" color="red">{{ t.icon }}</v-icon>
    <div class="font-size-m">{{ t.name }}</div>
  </div>
</template>

<script>
export default {
  props: {
    t: Object,
    i: Number,
  },
};
</script>

<style lang="scss" scoped>
.font-size-m {
  font-size: 14px;
  color: #9f4c38;
}
</style>