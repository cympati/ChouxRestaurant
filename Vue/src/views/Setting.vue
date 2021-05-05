<template>
  <div id="setting" class="px-10 pt-1">
    <Layout :page="name">
      <VNavLeft />
      <v-card class="pa-10 ma-1" v-if="getIsLogin">
        <v-container>
          <v-col>
            <v-card class="d-flex flex-column pa-5">
              <v-card-title class="ml-3"> My Profile </v-card-title>
              <div class="d-flex flex-row justify-space-around align-center">
                <div
                  class="d-flex flex-column justify-space-around align-center"
                >
                  <div class="mb-9">
                    <v-avatar size="300" :color="getColorSelect.value">
                      <span class="white--text font-name-size">{{
                        getTwoChar
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
                      style="width: 130.5px"
                      outlined
                      rounded
                      dense
                      color="black"
                      @change="setColorSelect(colorSelect)"
                    ></v-select>
                  </div>
                </div>

                <div class="box-info">
                  <v-container>
                    <v-form
                      ref="infoUserForm"
                      v-model="validForm"
                      lazy-validation
                    >
                      <v-row>
                        <v-col cols="12" sm="12" md="12">
                          <h4>Firstname</h4>
                          <v-text-field
                            v-model="infoUser.firstName"
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
                            v-model="infoUser.lastName"
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
                            v-model="infoUser.email"
                            :rules="[rules.required]"
                            prepend-icon="mdi-email"
                            required
                            clearable
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="12" md="12">
                          <h4>Phone Number</h4>
                          <v-text-field
                            v-model="infoUser.phoneNumber"
                            :rules="phoneNumberRules"
                            prepend-icon="mdi-phone"
                            required
                            clearable
                            maxlength="10"
                            hint="0XXXXXXXXX"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-container>
                </div>
              </div>
            </v-card>
          </v-col>
          <v-divider class="my-9"></v-divider>

          <v-col v-if="!getIsAdmin">
            <v-card class="pa-5">
              <v-card-title class="ml-3">Contact preference</v-card-title>
              <ContactPreferenceContainer
                :checkbox="checkbox"
                @changeCheck="checkbox = !checkbox"
              />
            </v-card>
          </v-col>
          <v-divider class="my-9" v-if="!getIsAdmin"></v-divider>
          <v-col>
            <v-card class="pa-5">
              <v-card-title class="ml-3"> Change password </v-card-title>
              <v-card-text>
                <v-container>
                  <v-form
                    ref="newPasswordForm"
                    v-model="validNewPasswordFrom"
                    lazy-validation
                  >
                    <NewPasswdContainer
                      :rules="rules"
                      :setNewPasswd="setNewPasswd"
                      :setConfirmPasswd="setConfirmPasswd"
                    />
                  </v-form>
                </v-container>
              </v-card-text>
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
      <div v-else>
        <span
          >You need to <a @click="setDialogLogin(true)">LOG IN</a> before</span
        >
      </div>

      <!-- Dialog Confirm -->
      <ConfirmDialog
        :dialogConfirm="dialogConfirm"
        :setMatch="setMatch"
        :getMatch="getMatch"
        :rules="rules"
        :checkConfirmPassword="confirmPassword"
        @changeDialogConfirm="dialogConfirm = false"
        @changeInfo="changeInfo"
      />

      <ValidSnackbar
        :valid="getValidSnb.dialog"
        :textValid="getValidSnb.text"
        @closeDialog="setDialogSnbValid(false)"
      />
      <InvalidSnackbar
        :invalid="getInvalidSnb.dialog"
        :textInvalid="getInvalidSnb.text"
        @closeDialog="setDialogSnbInvalid(false)"
      />
    </Layout>
    <Login />
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  components: {
    Login: () => import("../components/LoginPage/Login"),
    Layout: () => import("../components/Navbars/Layout"),
    VNavLeft: () => import("../components/Navbars/VNavLeft"),
    ConfirmDialog: () =>
      import("../components/AllDialogs/ConfirmDialogSetting"),
    NewPasswdContainer: () =>
      import("../components/SettingPage/NewPasswdContainer"),
    ContactPreferenceContainer: () =>
      import("../components/SettingPage/ContactPreferenceContainer"),
    ValidSnackbar: () => import("../components/Snackbars/ValidSnackbar"),
    InvalidSnackbar: () => import("../components/Snackbars/InvalidSnackbar"),
  },
  data() {
    return {
      name: "Setting",
      checkbox: false,
      validForm: true,
      dialogConfirm: false,
      multiLine: true,
      validNewPasswordFrom: true,
      snackbarConfirmPasswordValid: false,
      notificationTextConfirmPasswordValid: "Your changes are saved",
      show: false,
      colorSelect: {},
      colors: [
        { color: "Brown", value: "brown" },
        { color: "Red", value: "red" },
        { color: "Purple", value: "purple" },
        { color: "Green", value: "green" },
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
      infoUser: {
        firstName: "user",
        lastName: "user",
        id: "0",
        email: "user",
        phoneNumber: "0",
        isAdmin: false,
      },
    };
  },

  methods: {
    ...mapActions("account", [
      "setDialogSnbValid",
      "setColorSelect",
      "setDialogLogin",
      "setMatch",
      "confirmPassword",
      "setDialogSnbInvalid",
      "editPassword",
      "editProfile",
      "setInvalidSnb",
      "setNewPasswd",
      "setConfirmPasswd",
    ]),
    async changeInfo() {
      const info = {
        color: this.getColorSelect.color,
        firstName: this.infoUser.firstName,
        lastName: this.infoUser.lastName,
        email: this.infoUser.email,
        phone: this.infoUser.phoneNumber,
        getReminders: this.checkbox ? 1 : 0,
      };
      console.log("Check changed Information");
      if (
        this.getNewPasswordForm.new === this.getNewPasswordForm.confirm &&
        this.getNewPasswordForm.new !== "" &&
        this.getNewPasswordForm.confirm !== "" &&
        this.$refs.infoUserForm.validate()
      ) {
        console.log(this.getMatch);
        console.log(this.getNewPasswordForm);
        console.log("change information and new password");

        console.log(info);
        console.log(this.getNewPasswordForm.new);
        // change information and new password (Backend)
        await this.editPassword(this.getNewPasswordForm.new);
        await this.editProfile(info);
        // location.reload();
      } else if (
        (this.getNewPasswordForm.new !== this.getNewPasswordForm.confirm ||
          this.getNewPasswordForm.new === "" ||
          this.getNewPasswordForm.confirm === "") &&
        this.$refs.infoUserForm.validate()
      ) {
        console.log(this.getMatch);
        console.log(this.getNewPasswordForm);
        console.log("change information only");

        console.log(info);
        // change information only
        await this.editProfile(info);
      } else {
        // getMatch === false
        console.log(this.getMatch);
        console.log(this.getNewPasswordForm);
        let snackbar = {
          dialog: true,
          text: "Your information is required :(",
        };
        this.setInvalidSnb(snackbar);
      }
    },
  },
  mounted() {
    this.infoUser = this.getInfoUser.userDetail;
    this.colorSelect = this.getColorSelect.value;
    this.checkbox = this.getGetReminders;
  },
  computed: {
    ...mapGetters("account", [
      "getGetReminders",
      "getColorSelect",
      "getIsLogin",
      "getInfoUser",
      "getTwoChar",
      "getMatch",
      "getNewPasswordForm",
      "getInvalidSnb",
      "getValidSnb",
      "getIsAdmin",
    ]),
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
