<template>
  <div class="input-wrap">
    <h1>Make a reservation</h1>
    <div class="px-10">
      <v-menu
        ref="menu"
        v-model="menu"
        transition="scale-transition"
        origin="center center"
        width="auto"
      >
        <!-- Date -->
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            ref="dateForm"
            clearable
            solo
            v-model="date"
            v-bind="attrs"
            v-on="on"
            required
            label="Date"
          ></v-text-field>
        </template>
        <v-date-picker v-model="date" no-title color="blue"> </v-date-picker>
      </v-menu>

      <!-- Time -->
      <v-select
        ref="timeForm"
        v-model="reserveDetails.time"
        auto-select-first
        clearable
        solo
        :items="[
          '10:00 AM',
          '10:30 AM',
          '11:00 AM',
          '11:30 AM',
          '12:00 PM',
          '12:30 PM',
          '01:00 PM',
          '01:30 PM',
          '02:00 PM',
          '02:30 PM',
          '03:00 PM',
          '03:30 PM',
          '04:00 PM',
          '04:30 PM',
          '05:00 PM',
          '05:30 PM',
          '06:00 PM',
          '06:30 PM',
          '07:00 PM',
          '07:30 PM',
          '08:00 PM',
        ]"
        label="Time"
        required
      ></v-select>

      <!-- Party Size -->
      <v-select
        ref="sizeForm"
        v-model="reserveDetails.partySize"
        auto-select-first
        clearable
        solo
        :items="['1', '2', '3', '4', '5', '6']"
        label="Party size"
        required
      ></v-select>

      <v-btn
        app
        color="blue"
        @click="dialogFindTable = !dialogFindTable"
        dark
        large
        >Find a table</v-btn
      >
      <DialogFindTable
        v-if="dialogFindTable"
        :dialog="dialogFindTable"
        :infoDetails="infoDetails"
        :reserveDetails="reserveDetails"
        :date="date"
        @closeDialogFindATable="dialogFindTable = false"
        @resetForm="resetForm"
      />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      infoDetails: {
        // User
        firstName: "Patiphon",
        lastName: "Klangpraphan",
        id: "234",
        email: "cympati@gmail.com",
        password: "28052545",
        phoneNumber: "0956600463",
        isAdmin: 0,
      },
      reserveDetails: {
        reserveId: "000",
        date: {
          year: "",
          month: "",
          day: "",
        },
        time: "",
        partySize: "",
        specialRequests: "",
        // 0 = pending, 1 = come, 2 = not come, 3 = complete, 4 = cancel
        status: 0,
      },
      date: new Date().toISOString().substr(0, 10),
      menu: false,
      dialogFindTable: false,
    };
  },
  components: {
    DialogFindTable: () => import("../AllDialogs/DialogFindTable"),
  },
  methods: {
    save(date) {
      this.$refs.menuForm.save(date);
    },
    resetForm() {
      this.$refs.dateForm.reset();
      this.$refs.timeForm.reset();
      this.$refs.sizeForm.reset();
    },
  },
  mounted() {
    this.reserveDetails.date = {
      year: this.date.substring(0, 4),
      month: this.date.substring(5, 7),
      day: this.date.substring(8),
    };
  },
};
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@300;400;600;700;800;900&display=swap");
* {
  font-family: "Nunito Sans", sans-serif;
}
div {
  display: flex;
  flex-direction: column;
  text-align: center;
  h1 {
    color: white;
    font-weight: 900;
    margin: 4.5rem 0 2.8rem 0;
  }
}
</style>