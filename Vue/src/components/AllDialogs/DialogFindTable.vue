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
            <v-form ref="reserveForm" v-model="validFrom" lazy-validation>
              <v-row>
                <!-- firstname -->
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    v-model="getInfoUser.userDetail.firstname"
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
                    v-model="getInfoUser.userDetail.lastname"
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
                      <!-- <v-form
                      ref="reserveForm"
                      v-model="validFrom"
                      lazy-validation
                    > -->
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
                      <!-- </v-form> -->
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
                  <!-- <v-form ref="reserveForm" v-model="validFrom" lazy-validation> -->
                  <v-autocomplete
                    v-model="time"
                    ref="timeForm"
                    auto-select-first
                    clearable
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
                    requiredInfo
                    :rules="[rules.requiredInfo]"
                    prepend-icon="mdi-clock-time-eight-outline"
                  ></v-autocomplete>
                  <!-- </v-form> -->
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
                  <!-- <v-form ref="reserveForm" v-model="validFrom" lazy-validation> -->
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
                  <!-- </v-form> -->
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
                  <v-form ref="reqForm" v-model="validFrom" lazy-validation>
                    <v-text-field
                      v-model="reserveDetails.specialRequests"
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
                  >(not {{ getInfoUser.userDetail.firstname }}?)</a
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
    />
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import moment from "moment";
export default {
  data() {
    return {
      validFrom: true,
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
    };
  },
  components: {
    VCardTitle: () => import("../JubJibComponent/VCardTitle"),
    Login: () => import("../LoginPage/Login"),
  },
  mounted() {
    this.time = this.reserveDetails.time;
    this.size = this.reserveDetails.partySize;
    this.datersv = this.date;

    let totalTime = 300;
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
    resetForm: Function,
  },
  computed: {
    ...mapGetters("account", ["getInfoUser"]),
  },
  methods: {
    ...mapActions("account", ["setDialogLogin"]),
    async reserveLoading() {
      this.loading = true;
      await new Promise((res) => {
        setTimeout(() => [res(), (this.loading = false), this.save()], 3000);
      });
    },
    save() {
      console.log("Confirm Reservation Already!");
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
    allowedDates(val) {
      let nowStr = moment().format().substring(0, 10);
      let now = new Date(nowStr).getTime();
      let date = new Date(val).getTime();
      return now <= date;
    },
    // parseInt(val.split("-")[2], 10) >= new Date().getDate().split("-")[2],
    // console.log(new Date(val), new Date())
    goToLogin() {
      this.setDialogLogin(true);
      this.$emit("closeDialogFindATable");
    },
  },
};
</script>

<style>
</style>