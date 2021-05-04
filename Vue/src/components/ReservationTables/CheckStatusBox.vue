<template>
  <div>
    <div v-if="item.status === 'pending'" class="pa-0">
      <v-icon small class="mr-7" @click="approveCP"> mdi-check-bold </v-icon>
      <v-icon small @click="approveCC" class="pa-0"> mdi-close-thick </v-icon>
    </div>
    <!-- Check complete -->
    <div v-else-if="item.status === 'complete'">
      <span style="color: green"> completed </span>
    </div>
    <!-- Check cancel -->
    <div v-else-if="item.status === 'cancel'">
      <span style="color: #9f4c38">cancelled</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    item: Object,
    i: Number,
    approveRsv: Function,
  },

  methods: {
    approveCP() {
      let info = {
        status: 1,
        id: this.item.id,
      };
      this.approveRsv(info);
    },
    approveCC() {
      let info = {
        status: 2,
        id: this.item.id,
      };
      this.approveRsv(info);
    },
  },
};
</script>