<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="infoDetails"
      :search="search"
      item-key="id"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Information</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search by id"
            single-line
            hide-details
          ></v-text-field>
        </v-toolbar>
      </template>

      <!-- Contents -->
      <template v-slot:body="{ items }">
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
              <CheckStatusBox
                :i="i"
                :item="item"
                @changeToComplete="changeToComplete"
                @changeToCancel="changeToCancel"
              />
            </td>
            <td></td>
          </tr>
        </tbody>
      </template>
    </v-data-table>
  </div>
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
    CheckStatusBox: () => import("../ReservationTables/CheckStatusBox"),
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
    changeToComplete(item) {
      item.statusComplete = true;
      item.status = "complete";
    },
    changeToCancel(item) {
      item.statusCancel = true;
      item.status = "cancel";
    },
  },
};
</script>