<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="allReserve"
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
      <template v-slot:body="{ items }" v-if="allReserve.length != 0">
        <tbody>
          <tr v-for="(item, i) in items" :key="i">
            <td>{{ item.id }}</td>
            <td>{{ item.dateTime.substring(0, 10) }}</td>
            <td>{{ item.dateTime.substring(10, 16) }}</td>
            <td>{{ item.firstName + " " + item.lastName }}</td>

            <!-- More info -->
            <td>
              <!-- Dialog More -->
              <MoreDialogAdmin :i="i" :item="item" />
            </td>
            <td>
              <CheckStatusBox :i="i" :item="item" :approveRsv="approveRsv" />
            </td>
            <td></td>
          </tr>
        </tbody>
      </template>
      <template v-slot:no-data v-else>
        <span>No Reservations</span>
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
    allReserve: Array,
    approveRsv: Function,
  },
  components: {
    MoreDialogAdmin: () => import("../AllDialogs/MoreDialogAdmin"),
    CheckStatusBox: () => import("../ReservationTables/CheckStatusBox"),
  },
};
</script>