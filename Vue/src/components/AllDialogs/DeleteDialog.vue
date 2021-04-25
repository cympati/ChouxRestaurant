<template>
  <div>
    <template>
      <v-icon small @click="deleteItem(item)">
        mdi-close-circle-outline
      </v-icon>
    </template>
    <!-- dialogDelete -->
    <v-dialog v-model="dialogDelete" max-width="580px" persistent>
      <v-card>
        <v-card-title class="headline"
          >Are you sure you want to cancel this reservation?
        </v-card-title>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red lighten-1" text @click="closeDelete">Cancel</v-btn>
          <v-btn color="success darken-1" text @click="deleteItemConfirm(item)"
            >OK</v-btn
          >
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
      editedIndex: -1,
      editedItem: {
        id: "000",
        detail: {
          firstName: "",
          lastName: "",
          partySize: 0,
          email: "",
          phoneNumber: "",
          specialRequests: "",
        },
        date: {
          year: 0,
          month: 0,
          day: 0,
        },
        time: "",
        status: "upcoming",
        statusComplete: false,
        statusCancel: false,
      },
      defaultItem: {
        id: "000",
        detail: {
          firstName: "",
          lastName: "",
          partySize: 0,
          email: "",
          phoneNumber: "",
          specialRequests: "",
        },
        date: {
          year: 0,
          month: 0,
          day: 0,
        },
        time: "",
        status: "upcoming",
        statusComplete: false,
        statusCancel: false,
      },
    };
  },
  methods: {
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    deleteItemConfirm(item) {
      this.infoDetails.splice(this.editedIndex, 1);
      item.status = "cancel";
      item.statusCancel = true;
      this.closeDelete();
    },
    deleteItem(item) {
      this.editedIndex = this.infoDetails.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
  },
  props: {
    infoDetails: Array,
    i: Number,
    item: Object,
  },
};
</script>

<style>
</style>