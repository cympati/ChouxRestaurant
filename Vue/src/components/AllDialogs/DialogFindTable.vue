<template>
  <div>
    <v-dialog v-model="dialog" max-width="680px" persistent>
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
                <!-- firstname -->
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

                <!-- lastname -->
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    v-model="getInfoUser.userDetail.lastName"
                    label="Lastname"
                    prepend-icon="mdi-account-multiple-outline"
                    readonly
                    required
                    requiredInfo
                    :rules="[rules.requiredInfo]"
                  ></v-text-field>
                </v-col>
                <!-- <v-col cols="12" sm="12" md="12"> -->
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
                        v-model="datersv"
                        ref="dateForm"
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
                      :allowed-dates="allowedDates"
                      v-model="datersv"
                      no-title
                      color="blue"
                      width="300"
                    >
                    </v-date-picker>
                  </v-menu>
                </v-col>

                <!-- time -->
                <v-col cols="12" sm="6" md="6">
                  <v-autocomplete
                    v-model="time"
                    ref="timeForm"
                    auto-select-first
                    clearable
                    :items="times"
                    item-text="time"
                    item-value="val"
                    label="Time"
                    requiredInfo
                    :rules="[rules.requiredInfo]"
                    prepend-icon="mdi-clock-time-eight-outline"
                  ></v-autocomplete>
                </v-col>
                <!-- </v-col> -->

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

                <!-- party size -->
                <v-col cols="12" sm="6" md="6">
                  <v-select
                    ref="sizeForm"
                    v-model="size"
                    :items="['1', '2', '3', '4', '5', '6']"
                    label="Party size"
                    required
                    requiredInfo
                    :rules="[rules.requiredInfo]"
                    prepend-icon="mdi-account-group"
                  ></v-select>
                </v-col>

                <!-- Email -->
                <v-col cols="12" sm="12" md="12">
                  <v-text-field
                    v-model="getInfoUser.userDetail.email"
                    prepend-icon="mdi-email"
                    label="Your email"
                    readonly
                    required
                    requiredInfo
                    :rules="[rules.requiredInfo]"
                  ></v-text-field>
                </v-col>

                <!-- Special Requests -->
                <v-col cols="12" sm="12" md="12">
                  <v-form ref="reqForm" v-model="validForm" lazy-validation>
                    <v-text-field
                      v-model="specialReq"
                      label="Special requests"
                      prepend-icon="mdi-email-newsletter"
                      clearable
                    ></v-text-field>
                  </v-form>
                </v-col>
              </v-row>
              <small style="color: #212123"
                >*please, carefully check your details
                <a style="color: #9f4c38" @click="goToLogin"
                  >(not {{ getInfoUser.userDetail.firstName }}?)</a
                ></small
              >
            </v-form>
          </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <div>
            <v-btn
              color="red lighten-1"
              text
              @click="$emit('closeDialogFindATable')"
            >
              Close
            </v-btn>
            <v-btn color="green darken-1" text @click="validateForm">
              Confirm
            </v-btn>
          </div>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <Login />
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  data() {
    return {
      validForm: true,
      loading: false,
      menu: false,
      dialogConfirm: false,
      rules: {
        requiredInfo: (value) => !!value || "Required",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
      minutes: "05",
      second: "00",
      cardTitle: "Make a reservation",
      time: "",
      size: "",
      datersv: "",
      specialReq: "",
    };
  },
  components: {
    VCardTitle: () => import("../JubJibComponent/VCardTitle"),
    Login: () => import("../LoginPage/Login"),
  },
  mounted() {
    console.log(this.reserveDetails);
    this.time = this.reserveDetails.time;
    this.size = this.reserveDetails.size;
    console.log(this.time);
    console.log(this.date);
    this.datersv = this.date;

    let totalTime = 300; // 5min
    if (totalTime > 0) {
      setInterval(() => {
        this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
        totalTime -= 1;
        this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
        this.second = ("0" + (totalTime % 60)).slice(-2);
        if (totalTime <= 0) {
          this.infoDetails = {};
          this.$emit("closeDialogFindATable");
        }
      }, 1000);
    }
  },
  props: {
    dialog: Boolean,
    date: String,
    reserveDetails: Object,
    allowedDates: Function,
    times: Array,
  },
  computed: {
    ...mapGetters("account", ["getInfoUser"]),
  },
  methods: {
    ...mapActions("account", ["setDialogLogin"]),
    ...mapActions("reserve", ["addRsv"]),
    async reserveLoading() {
      this.loading = true;
      await new Promise((res) => {
        setTimeout(() => [res(), (this.loading = false), this.save()], 2000);
      });
    },
    async save() {
      console.log("Confirm Reservation Already!");
      this.reserveDetails.date = {
        year: this.datersv.substring(0, 4),
        month: this.datersv.substring(5, 7),
        day: this.datersv.substring(8),
      };
      let date_time = new Date(
        this.reserveDetails.date.year,
        this.reserveDetails.date.month - 1,
        this.reserveDetails.date.day,
        this.time.h,
        this.time.m,
        0
      ).getTime();
      console.log(date_time);
      let newReserve = {
        dt: date_time,
        size: this.size,
        req: this.specialReq,
      };
      console.log(newReserve);
      await this.addRsv(newReserve);
    },
    async validateForm() {
      if (this.$refs.reserveForm.validate()) {
        await this.reserveLoading();
        this.$refs.timeForm.reset();
        this.$refs.sizeForm.reset();
        this.$refs.reqForm.reset();
        this.$refs.dateForm.reset();
        this.$refs.reserveForm.resetValidation();
        this.$emit("resetForm");
        this.$emit("closeDialogFindATable");
      }
    },
    goToLogin() {
      this.setDialogLogin(true);
      this.$emit("closeDialogFindATable");
    },
  },
};
</script>

<style>
</style>