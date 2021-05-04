<template>
  <v-data-table
    :headers="headers"
    :items="allReserve"
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
    <template v-slot:body="{ items }" v-if="allReserve">
      <tbody>
        <tr v-for="(item, i) in items" :key="i">
          <td>{{ item.id }}</td>
          <td>{{ item.dateTime.substring(0, 10) }}</td>
          <td>{{ item.dateTime.substring(10, 16) }}</td>
          <td>
            {{ item.firstName + " " + item.lastName }}
          </td>

          <!-- More info -->
          <td>
            <!-- Dialog More -->
            <MoreDialogAdmin :i="i" :item="item" />
          </td>

          <td>
            <template>
              <div v-if="item.status == 'complete'" style="color: green">
                <span>completed</span>
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
    allReserve: Array,
  },
  components: {
    MoreDialogAdmin: () => import("../AllDialogs/MoreDialogAdmin"),
  },
  methods: {},
};
</script>

<style>
</style>