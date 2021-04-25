<template>
  <v-data-table
    :headers="headers"
    :items="infoDetails"
    :search="search"
    item-key="id"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Upcoming Reservation</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search by id"
          single-line
          hide-details
        ></v-text-field>
        <v-divider class="mx-4" inset vertical></v-divider>
        <!-- Add Reservation -->
        <v-btn
          color="primary"
          dark
          class="mb-2"
          @click="dialogAdd = !dialogAdd"
          @closeDialogAdd="dialogAdd = !dialogAdd"
        >
          Add
        </v-btn>
        <AddDialog
          :infoDetails="infoDetails"
          v-if="dialogAdd"
          :dialogAddReserve="dialogAdd"
          @closeDialogAdd="dialogAdd = !dialogAdd"
        />
      </v-toolbar>
    </template>

    <!-- Contents -->
    <template v-slot:body="{ items }" v-if="infoDetails.length > 0">
      <tbody>
        <tr v-for="(item, i) in items" :key="i">
          <td>{{ item.id }}</td>
          <td>{{ dateString(i) }}</td>
          <td>{{ item.time }}</td>
          <td>{{ nameUser(item) }}</td>

          <td>
            <!-- Dialog More -->
            <MoreDialogUser :i="i" :item="item" />
          </td>

          <td>
            <span style="color: #6079d6">{{ item.status }}</span>
          </td>

          <td>
            <DeleteDialog :i="i" :item="item" :infoDetails="infoDetails" />
          </td>
        </tr>
      </tbody>
    </template>
    <template v-slot:no-data v-else>
      <p>No Upcoming Reservations</p>
    </template>
  </v-data-table>
</template>

<script>
export default {
  data() {
    return {
      dialogAdd: false,
      search: "",
    };
  },
  props: {
    headers: Array,
    infoDetails: Array,
  },
  components: {
    AddDialog: () => import("../AllDialogs/AddDialog"),
    DeleteDialog: () => import("../AllDialogs/DeleteDialog"),
    MoreDialogUser: () => import("../AllDialogs/MoreDialogUser"),
  },
  methods: {
    dateString(i) {
      if (
        this.infoDetails[i].date.month < 10 &&
        this.infoDetails[i].date.day < 10
      ) {
        return (
          this.infoDetails[i].date.year +
          "-" +
          "0" +
          this.infoDetails[i].date.month +
          "-" +
          "0" +
          this.infoDetails[i].date.day
        );
      } else if (this.infoDetails[i].date.month < 10) {
        return (
          this.infoDetails[i].date.year +
          "-" +
          "0" +
          this.infoDetails[i].date.month +
          "-" +
          this.infoDetails[i].date.day
        );
      } else if (this.infoDetails[i].date.day < 10) {
        return (
          this.infoDetails[i].date.year +
          "-" +
          this.infoDetails[i].date.month +
          "-" +
          "0" +
          this.infoDetails[i].date.day
        );
      } else {
        return (
          this.infoDetails[i].date.year +
          "-" +
          this.infoDetails[i].date.month +
          "-" +
          this.infoDetails[i].date.day
        );
      }
    },
    nameUser(item) {
      return item.detail.firstName + " " + item.detail.lastName;
    },
  },
};
</script>

<style>
</style>