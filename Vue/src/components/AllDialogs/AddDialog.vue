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
          <v-row>
            <!-- firstname -->
            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="editedItem.detail.firstName"
                label="Firstname"
                prepend-icon="mdi-account"
                readonly
                required
                clearable
                requiredInfo
                :rules="[rules.requiredInfo]"
              ></v-text-field>
            </v-col>

            <!-- lastname -->
            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="editedItem.detail.lastName"
                label="Lastname"
                required
                clearable
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
                <v-date-picker v-model="date" no-title color="blue" width="300">
                </v-date-picker>
              </v-menu>
            </v-col>

            <!-- time -->
            <v-col cols="12" sm="6" md="6">
              <v-autocomplete
                v-model="editedItem.time"
                prepend-icon="mdi-clock-time-eight-outline"
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
              ></v-autocomplete>
            </v-col>

            <!-- Phone number -->
            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="editedItem.detail.phoneNumber"
                label="Phone number"
                prepend-icon="mdi-phone"
                readonly
                required
                clearable
                requiredInfo
                :rules="[rules.requiredInfo]"
              ></v-text-field>
            </v-col>

            <!-- party size -->
            <v-col cols="12" sm="6" md="6">
              <v-select
                v-model="editedItem.detail.partySize"
                :items="['1', '2', '3', '4', '5', '6']"
                label="Party size"
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
                v-model="editedItem.detail.email"
                label="Your email"
                prepend-icon="mdi-email"
                readonly
                required
                clearable
                requiredInfo
                :rules="[rules.requiredInfo]"
              ></v-text-field>
            </v-col>

            <!-- Special Requests -->
            <v-col cols="12" sm="12" md="12">
              <v-text-field
                v-model="editedItem.specialRequests"
                label="Special requests"
                clearable
                prepend-icon="mdi-email-newsletter"
              ></v-text-field>
            </v-col>
          </v-row>
          <small style="color: #212123"
            >*please, carefully check your details
            <a
              style="color: #9f4c38"
              @click="[$store.dispatch('setDialogLogin', true), close()]"
              >(not {{ $store.getters.getInfoUser.userDetail.firstName }}?)</a
            ></small
          >
        </v-container>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="red lighten-1" text @click="close"> Cancel </v-btn>
        <v-btn color="success darken-1" text @click="reserveLoading">
          Confirm
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      selection: 1,
      menu: false,
      rules: {
        requiredInfo: (value) => !!value || "Required",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
      minutes: "05",
      second: "00",
      cardTitle: "Make a reservation",
      editedIndex: -1,
      date: new Date().toISOString().substr(0, 10),
      defaultItem: {
        id: "000",
        detail: {
          firstName: "",
          lastName: "",
          partySize: 0,
          email: "",
          phoneNumber: "",
          specialRequests: "",
        },
        date: {
          year: 0,
          month: 0,
          day: 0,
        },
        time: "",
        status: "upcoming",
        statusComplete: false,
        statusCancel: false,
      },
      editedItem: {
        id: "000",
        detail: {
          firstName: "",
          lastName: "",
          partySize: 0,
          email: "",
          phoneNumber: "",
          specialRequests: "",
        },
        date: {
          year: 0,
          month: 0,
          day: 0,
        },
        time: "",
        status: "complete",
        statusComplete: false,
        statusCancel: false,
      },
    };
  },
  props: {
    infoDetails: Array,
    dialogAddReserve: Boolean,
  },
  components: {
    VCardTitle: () => import("../JubJibComponent/VCardTitle"),
  },
  methods: {
    reserveLoading() {
      this.loading = true;
      setTimeout(
        () => [
          (this.loading = false),
          (this.dialogConfirm = true),
          this.save(),
        ],
        2000
      );
    },
    close() {
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
      this.$emit("closeDialogAdd");
      // console.log("execute");
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.infoDetails[this.editedIndex], this.editedItem);
        console.log(this.infoDetails[this.editedIndex]);
      } else {
        this.infoDetails.push(this.editedItem);
        // มันส่งกลับไปที่หน้า Upcomming ไม่ใช่หน้ากลาง
        // console.log(this.editedItem);
        // console.log(this.infoDetails);
      }
      this.close();
    },
  },
  mounted() {
    this.editedItem.date = {
      year: this.date.substring(0, 4),
      month: this.date.substring(5, 7),
      day: this.date.substring(8),
    };

    let totalTime = 300;
    if (totalTime > 0) {
      setInterval(() => {
        this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
        totalTime -= 1;
        this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
        this.second = ("0" + (totalTime % 60)).slice(-2);
        if (totalTime <= 0) {
          this.dialogAddReserve = false;
          // this.infoDetails = Object.assign({}, this.defaultItem);
          // this.infoDetails = this.defaultItem;
        }
      }, 1000);
    }
  },
};
</script>

<style>
</style>