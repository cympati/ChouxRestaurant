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
        <v-btn color="primary" dark class="mb-2" @click="dialogAdd = true">
          Add
        </v-btn>
        <AddDialog
          v-if="dialogAdd"
          :setDialogLogin="setDialogLogin"
          :getInfoUser="getInfoUser"
          :dialogAddReserve="dialogAdd"
          :addRsv="addRsv"
          @closeDialogAdd="dialogAdd = false"
        />
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

          <td>
            <MoreDialogUser
              :i="i"
              :item="item"
              :confirmPassword="confirmPassword"
              :getMatch="getMatch"
              :setMatch="setMatch"
              :editRsv="editRsv"
            />
          </td>

          <td>
            <span style="color: #6079d6">{{ item.status }}</span>
          </td>

          <td>
            <DeleteDialog :i="i" :item="item" :approveRsv="approveRsv" />
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
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      dialogAdd: false,
      search: "",
    };
  },
  props: {
    headers: Array,
    allReserve: Array,
  },
  computed: {
    ...mapGetters("account", ["getMatch", "getInfoUser"]),
  },
  components: {
    AddDialog: () => import("../AllDialogs/AddDialog"),
    DeleteDialog: () => import("../AllDialogs/DeleteDialog"),
    MoreDialogUser: () => import("../AllDialogs/MoreDialogUser"),
  },
  methods: {
    ...mapActions("account", ["confirmPassword", "setMatch", "setDialogLogin"]),
    ...mapActions("reserve", ["editRsv", "approveRsv", "addRsv"]),
  },
};
</script>

<style>
</style>