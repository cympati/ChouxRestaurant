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
        <v-toolbar-title>Past Reservation</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
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

          <!-- More info -->
          <td>
            <!-- Dialog More -->
            <MoreDialogAdmin :i="i" :item="item" />
          </td>

          <td>
            <template>
              <div v-if="item.status == 'complete'" style="color: green">
                <span>{{ item.status }}</span>
              </div>
              <div v-else-if="item.status == 'cancel'" style="color: #9f4c38">
                <span>cancelled</span>
              </div>
            </template>
          </td>
          <td></td>
        </tr>
      </tbody>
    </template>
    <template v-slot:no-data v-else>
      <span>No Past Reservations</span>
    </template>
  </v-data-table>
</template>

<script>
export default {
  data() {
    return {
      search: "",
    };
  },
  props: {
    headers: Array,
    infoDetails: Array,
  },
  components: {
    MoreDialogAdmin: () => import("../AllDialogs/MoreDialogAdmin"),
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