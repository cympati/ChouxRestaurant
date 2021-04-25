<template>
  <div id="setting" class="px-10 pt-1">
    <Layout :page="name">
      <VNavLeft />
      <v-card class="pa-10 ma-5" v-if="getIsLogin">
        <v-container>
          <v-col>
            <v-card-title class="d-flex flex-column pa-5">
              <div>
                <v-card-title class="ml-3"> My Profile </v-card-title>
              </div>
              <div class="d-flex flex-row justify-space-around align-center">
                <div
                  class="d-flex flex-column justify-space-around align-center"
                >
                  <div class="mb-9">
                    <v-avatar size="300" :color="getColorSelect.value">
                      <span class="white--text font-name-size">{{
                        nameAvatar
                      }}</span>
                    </v-avatar>
                  </div>
                  <div>
                    <v-select
                      v-model="colorSelect"
                      :items="colors"
                      item-text="color"
                      item-value="value"
                      label="Change color"
                      persistent-hint
                      return-object
                      single-line
                      style="width: 129px"
                      outlined
                      rounded
                      dense
                      color="black"
                    ></v-select>
                  </div>
                </div>

                <div class="box-info">
                  <v-container>
                    <v-row>
                      <!-- firstname -->
                      <v-col cols="12" sm="12" md="12">
                        <h4>Firstname</h4>
                        <v-text-field
                          v-model="dataSelect.userInfo.firstName"
                          :rules="[rules.required]"
                          prepend-icon="mdi-account"
                          required
                          clearable
                          maxlength="25"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="12" md="12">
                        <h4>Lastname</h4>
                        <v-text-field
                          v-model="dataSelect.userInfo.lastName"
                          :rules="[rules.required]"
                          prepend-icon="mdi-account-multiple-outline"
                          required
                          clearable
                          maxlength="25"
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="12" md="12">
                        <h4>Email Address</h4>
                        <v-text-field
                          v-model="dataSelect.userInfo.email"
                          :rules="[rules.required]"
                          prepend-icon="mdi-email"
                          required
                          clearable
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12" sm="12" md="12">
                        <h4>Phone Number</h4>
                        <!-- เปลี่ยน RegX phone number -->
                        <v-text-field
                          v-model="dataSelect.userInfo.phoneNumber"
                          :rules="phoneNumberRules"
                          prepend-icon="mdi-phone"
                          required
                          clearable
                          maxlength="10"
                          hint="0XXXXXXXXX"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </div>
              </div>
            </v-card-title>
          </v-col>
          <v-divider class="my-9"></v-divider>

          <v-col>
            <v-card class="pa-5">
              <v-card-title class="ml-3">Contact preference</v-card-title>
              <ContactPreferenceContainer />
            </v-card>
          </v-col>
          <v-divider class="my-9"></v-divider>
          <v-col>
            <v-card class="pa-5">
              <v-card-title class="ml-3"> Change password </v-card-title>
              <NewPasswdContainer :rules="rules" />
            </v-card>
          </v-col>
          <v-card-actions>
            <v-spacer></v-spacer>
            <div>
              <v-btn @click="dialogConfirm = true" color="primary">
                Save Change
              </v-btn>
            </div>
          </v-card-actions>
        </v-container>
      </v-card>

      <!-- Dialog Confirm -->
      <ConfirmDialog
        :dialogConfirm="dialogConfirm"
        @changeDialogConfirm="dialogConfirm = false"
        @changeInfo="changeInfo"
        @setColorBg="setColorSelect(colorSelect)"
      />

      <ValidSnackbar
        :valid="snackbarConfirmPasswordValid"
        :textValid="notificationTextConfirmPasswordValid"
        @changeValid="snackbarConfirmPasswordValid = false"
      />
    </Layout>
    <ValidSnackbar
      :valid="comingSoonSnb"
      :textValid="textComingSoon"
      @changeValid="comingSoonSnb = false"
    />
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    Layout: () => import("../components/Navbars/Layout"),
    VNavLeft: () => import("../components/Navbars/VNavLeft"),
    ValidSnackbar: () => import("../components/Snackbars/ValidSnackbar"),
    ConfirmDialog: () => import("../components/AllDialogs/ConfirmDialogMore"),
    NewPasswdContainer: () =>
      import("../components/SettingPage/NewPasswdContainer"),
    ContactPreferenceContainer: () =>
      import("../components/SettingPage/ContactPreferenceContainer"),
    // ProfileDrawer: () => import("../components/ReservationPage/ProfileDrawer"),
  },
  data() {
    return {
      name: "Setting",
      nameAvatar: "",
      dialogConfirm: false,
      comingSoonSnb: false,
      textComingSoon: "COMING SOON!",
      multiLine: true,
      snackbarConfirmPasswordValid: false,
      notificationTextConfirmPasswordValid: "Your changes are saved",
      show: false,
      colorSelect: [],
      colors: [
        { color: "Brown", value: "brown" },
        { color: "Red", value: "red" },
        { color: "Blue", value: "blue" },
        // { color: "Green", value: "teal darken-1" },
        { color: "Black", value: "black" },
      ],
      rules: {
        required: (value) => !!value || "Required.",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
      phoneNumberRules: [
        (v) => !!v || "Required",
        (v) => /^0\d{9}$/.test(v) || "Phone number must be valid",
      ],
      infoUser: {},
      dataSelect: {
        userInfo: {
          // User
          firstName: "",
          lastName: "",
          id: "",
          email: "",
          password: "",
          phoneNumber: "",
          isAdmin: 0,
        },
      },
      allUserData: [
        {
          userInfo: {
            // User
            firstName: "Patiphon",
            lastName: "Klangpraphan",
            id: "234",
            email: "cympati@gmail.com",
            password: "28052545",
            phoneNumber: "0956600463",
            isAdmin: 0,
          },

          allReserve: [
            {
              reserveId: "001",
              date: {
                year: "2002",
                month: "5",
                day: "28",
              },
              time: "11:00",
              partySize: "2",
              specialRequests: "Hungry so much!",
              // 0 = pending or upcoming, 1 = come, 2 = not come, 3 = complete, 4 = cancel
              status: 0,
            },
          ],
        },
      ],
    };
  },

  methods: {
    changeInfo() {
      this.snackbarConfirmPasswordValid = true;
      // change information (Backend)
      this.setColorSelect(this.colorSelect);
    },
    setColorSelect() {
      this.setColorSelect(this.colorSelect);
    },
  },
  mounted() {
    this.colorSelect = this.getColorSelect.value;
    console.log(this.getColorSelect.value);
    // let id = this.$route.params.id;
    // let dataSelect = this.allUserData.find((el) => el.userInfo.id == id);
    // this.dataSelect = dataSelect;
    this.infoUser = this.getInfoUser;
    this.nameAvatar = this.infoUser.userDetail.firstname
      .substring(0, 2)
      .toUpperCase();

    console.log(this.getInfoUser.userDetail.firstname);
  },
  computed: {
    ...mapGetters("account", [
      "getGetReminders",
      "getColorSelect",
      "getIsLogin",
      "getInfoUser",
    ]),
    ...mapActions("account", ["setGetReminders", "setColorSelect()"]),
  },
};
</script>

<style lang="scss" scoped>
h4 {
  font-weight: 400;
}
.container {
  max-width: 1100px;
}
.box-info {
  width: 500px;
}
.font-name-size {
  font-size: 110px;
  font-weight: 300;
}
</style>
