<template>
  <v-dialog v-model="dialogAddReserve" max-width="680px" persistent>
    <v-card :loading="loading">
      <template slot="progress">
        <v-progress-linear
          color="blue"
          height="10"
          indeterminate
        ></v-progress-linear>
      </template>
      <VCardTitle :cardTitle="cardTitle">
        <v-spacer></v-spacer>
        <span>{{ minutes }}:{{ second }}</span>
      </VCardTitle>
      <v-divider></v-divider>

      <v-card-text>
        <v-container>
          <v-form ref="reserveForm" v-model="validForm" lazy-validation>
            <v-row>
              <!-- Firstname -->
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="getInfoUser.userDetail.firstName"
                  label="Firstname"
                  prepend-icon="mdi-account"
                  readonly
                  required
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                ></v-text-field>
              </v-col>

              <!-- Lastname -->
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="getInfoUser.userDetail.lastName"
                  label="Lastname"
                  required
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                  readonly
                  prepend-icon="mdi-account-multiple-outline"
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="6" md="6">
                <v-menu
                  ref="menu"
                  v-model="menu"
                  transition="scale-transition"
                  open-on-click
                  width="auto"
                >
                  <!-- Date -->
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      clearable
                      ref="dateForm"
                      v-model="date"
                      v-bind="attrs"
                      v-on="on"
                      input
                      requiredInfo
                      :rules="[rules.requiredInfo]"
                      prepend-icon="mdi-calendar-range"
                      label="Date"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date"
                    no-title
                    color="blue"
                    width="300"
                    :allowed-dates="allowedDates"
                  >
                  </v-date-picker>
                </v-menu>
              </v-col>

              <!-- Time -->
              <v-col cols="12" sm="6" md="6">
                <v-autocomplete
                  v-model="reserveDetails.time"
                  prepend-icon="mdi-clock-time-eight-outline"
                  auto-select-first
                  ref="timeForm"
                  clearable
                  :items="times"
                  item-text="time"
                  item-value="val"
                  label="Time"
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                ></v-autocomplete>
              </v-col>

              <!-- Phone number -->
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="getInfoUser.userDetail.phoneNumber"
                  label="Phone number"
                  prepend-icon="mdi-phone"
                  readonly
                  required
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                ></v-text-field>
              </v-col>

              <!-- Party size -->
              <v-col cols="12" sm="6" md="6">
                <v-select
                  v-model="reserveDetails.size"
                  :items="['1', '2', '3', '4', '5', '6']"
                  label="Party size"
                  ref="sizeForm"
                  required
                  clearable
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                  prepend-icon="mdi-account-group"
                ></v-select>
              </v-col>

              <!-- Email -->
              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  v-model="getInfoUser.userDetail.email"
                  label="Your email"
                  prepend-icon="mdi-email"
                  readonly
                  required
                  requiredInfo
                  :rules="[rules.requiredInfo]"
                ></v-text-field>
              </v-col>

              <!-- Special Requests -->
              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  v-model="reserveDetails.specialReq"
                  ref="reqForm"
                  label="Special requests"
                  clearable
                  prepend-icon="mdi-email-newsletter"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-form>

          <small style="color: #212123"
            >Please book at least 30 minutes in advance, and carefully check
            your details
            <a
              style="color: #9f4c38"
              @click="[setDialogLogin(true), $emit('closeDialogAdd')]"
              >(not {{ getInfoUser.userDetail.firstName }}?)</a
            ></small
          >
        </v-container>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red lighten-1" text @click="$emit('closeDialogAdd')">
          Cancel
        </v-btn>
        <v-btn color="success darken-1" text @click="validateForm">
          Confirm
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from "moment";
export default {
  data() {
    return {
      menu: false,
      second: "00",
      minutes: "05",
      loading: false,
      validForm: true,
      cardTitle: "Make a reservation",
      date: new Date().toISOString().substr(0, 10),

      rules: {
        requiredInfo: (value) => !!value || "Required",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
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
        specialReq: "",
      },
    };
  },
  props: {
    addRsv: Function,
    getInfoUser: Object,
    setDialogLogin: Function,
    dialogAddReserve: Boolean,
  },
  components: {
    VCardTitle: () => import("../JubJibComponent/VCardTitle"),
  },
  methods: {
    async reserveLoading() {
      this.loading = true;
      await new Promise((res) => {
        setTimeout(() => [res(), (this.loading = false), this.save()], 2000);
      });
    },

    async validateForm() {
      if (this.$refs.reserveForm.validate()) {
        await this.reserveLoading();
        this.$refs.timeForm.reset();
        this.$refs.sizeForm.reset();
        this.$refs.reqForm.reset();
        this.$refs.dateForm.reset();
        this.$refs.reserveForm.resetValidation();
        this.$emit("closeDialogAdd");
        window.location.reload();
      }
    },

    async save() {
      this.reserveDetails.date = {
        year: this.date.substring(0, 4),
        month: this.date.substring(5, 7),
        day: this.date.substring(8),
      };
      let date_time = new Date(
        this.reserveDetails.date.year,
        this.reserveDetails.date.month - 1,
        this.reserveDetails.date.day,
        this.reserveDetails.time.h,
        this.reserveDetails.time.m,
        0
      ).getTime();
      let newReserve = {
        dt: date_time,
        size: this.reserveDetails.size,
        req: this.reserveDetails.specialReq,
      };
      await this.addRsv(newReserve);
    },

    allowedDates(val) {
      let nowStr = moment().format().substring(0, 10);
      let now = new Date(nowStr).getTime();
      let date = new Date(val).getTime();
      return now <= date;
    },
  },
  mounted() {
    this.reserveDetails.date = {
      year: this.date.substring(0, 4),
      month: this.date.substring(5, 7),
      day: this.date.substring(8),
    };

    let totalTime = 300; // 5min
    if (totalTime > 0) {
      setInterval(() => {
        this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
        totalTime -= 1;
        this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
        this.second = ("0" + (totalTime % 60)).slice(-2);
        if (totalTime <= 0) {
          this.$emit("closeDialogAdd");
        }
      }, 1000);
    }
  },
};
</script>
