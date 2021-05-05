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
        <v-date-picker
          :allowed-dates="allowedDates"
          v-model="date"
          no-title
          color="blue"
        >
        </v-date-picker>
      </v-menu>

      <!-- Time -->
      <v-select
        ref="timeForm"
        v-model="reserveDetails.time"
        auto-select-first
        clearable
        solo
        :items="times"
        item-text="time"
        item-value="val"
        label="Time"
        required
      ></v-select>

      <!-- Party Size -->
      <v-select
        ref="sizeForm"
        v-model="reserveDetails.size"
        auto-select-first
        clearable
        solo
        :items="['1', '2', '3', '4', '5', '6']"
        label="Party size"
        required
      ></v-select>

      <v-btn v-if="getIsAdmin" app color="blue" @click="showAdmin" dark large
        >Find a table</v-btn
      >
      <v-btn v-else app color="blue" @click="check" dark large
        >Find a table</v-btn
      >
      <DialogFindTable
        v-if="dialogFindTable"
        :dialog="dialogFindTable"
        :reserveDetails="reserveDetails"
        :date="date"
        :allowedDates="allowedDates"
        :times="times"
        @closeDialogFindATable="dialogFindTable = false"
        @resetForm="resetForm"
      />
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  data() {
    return {
      times: [
        { time: "10:00 AM", val: { h: "10", m: "00" } },
        { time: "10:30 AM", val: { h: "10", m: "30" } },
        { time: "11:00 AM", val: { h: "11", m: "00" } },
        { time: "11:30 AM", val: { h: "11", m: "30" } },
        { time: "12:00 PM", val: { h: "12", m: "00" } },
        { time: "12:30 PM", val: { h: "12", m: "30" } },
        { time: "01:00 PM", val: { h: "13", m: "00" } },
        { time: "01:30 PM", val: { h: "13", m: "30" } },
        { time: "02:00 PM", val: { h: "14", m: "00" } },
        { time: "02:30 PM", val: { h: "14", m: "30" } },
        { time: "03:00 PM", val: { h: "15", m: "00" } },
        { time: "03:30 PM", val: { h: "15", m: "30" } },
        { time: "04:00 PM", val: { h: "16", m: "00" } },
        { time: "04:30 PM", val: { h: "16", m: "30" } },
        { time: "05:00 PM", val: { h: "17", m: "00" } },
        { time: "05:30 PM", val: { h: "17", m: "30" } },
        { time: "06:00 PM", val: { h: "18", m: "00" } },
        { time: "06:30 PM", val: { h: "18", m: "30" } },
        { time: "07:00 PM", val: { h: "19", m: "00" } },
        { time: "07:30 PM", val: { h: "19", m: "30" } },
        { time: "08:00 PM", val: { h: "20", m: "00" } },
      ],
      reserveDetails: {
        date: {
          year: "",
          month: "",
          day: "",
        },
        time: "",
        size: "",
      },
      date: new Date().toISOString().substr(0, 10),
      menu: false,
      dialogFindTable: false,
    };
  },
  components: {
    DialogFindTable: () => import("../AllDialogs/DialogFindTable"),
  },
  props: {
    getIsAdmin: Boolean,
    getIsLogin: Boolean,
    setInvalidSnb: Function,
  },
  methods: {
    resetForm() {
      this.$refs.dateForm.reset();
      this.$refs.timeForm.reset();
      this.$refs.sizeForm.reset();
    },

    allowedDates(val) {
      let nowStr = moment().format().substring(0, 10);
      let now = new Date(nowStr).getTime();
      let date = new Date(val).getTime();
      return now <= date;
    },

    showAdmin() {
      let snackbar = {
        dialog: true,
        text: "You are admin :(",
      };
      this.setInvalidSnb(snackbar);
    },

    check() {
      if (this.getIsLogin) {
        this.dialogFindTable = !this.dialogFindTable;
      } else {
        let snackbar = {
          dialog: true,
          text: "Please login before :(",
        };
        this.setInvalidSnb(snackbar);
      }
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