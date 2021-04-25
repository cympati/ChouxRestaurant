<template>
  <div>
    <div
      v-if="item.statusComplete == false && item.statusCancel == false"
      class="pa-0"
    >
      <v-icon small class="mr-7" @click="$emit('changeToComplete', item)">
        mdi-check-bold
      </v-icon>
      <v-icon small @click="$emit('changeToCancel', item)" class="pa-0">
        mdi-close-thick
      </v-icon>
    </div>
    <!-- Check complete -->
    <div v-else-if="item.statusComplete == true && item.statusCancel == false">
      <span style="color: green">
        {{ item.status }}
      </span>
    </div>
    <!-- Check cancel -->
    <div v-else-if="item.statusCancel == true && item.statusComplete == false">
      <span style="color: #9f4c38">cancelled</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
    i: Number,
    changeToCancel: Function,
    changeToComplete: Function,
  },
};
</script>