<template>
  <div>
    <template>
      <v-icon small @click="dialogDelete = true">
        mdi-close-circle-outline
      </v-icon>
    </template>
    <v-dialog v-model="dialogDelete" max-width="580px" persistent>
      <v-card>
        <v-card-title class="headline"
          >Are you sure you want to cancel this reservation?
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red lighten-1" text @click="dialogDelete = false"
            >Close</v-btn
          >
          <v-btn color="success darken-1" text @click="deleteItem">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogDelete: false,
      show: false,
    };
  },
  methods: {
    async deleteItem() {
      let info = {
        id: this.item.id,
        status: 2,
      };
      await this.approveRsv(info);
      this.dialogDelete = false;
    },
  },
  props: {
    i: Number,
    item: Object,
    approveRsv: Function,
  },
};
</script>

<style>
</style>