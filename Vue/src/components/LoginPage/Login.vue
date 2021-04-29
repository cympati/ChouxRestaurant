<template>
  <div>
    <v-dialog
      v-model="getDialogLogin"
      max-width="620px"
      min-width="360px"
      persistent
    >
      <div>
        <CloseLoginDlg @resetForm="resetForm" />
        <v-tabs
          v-model="tab"
          show-arrows
          background-color="white"
          icons-and-text
          grow
          light
        >
          <v-tabs-slider color="red"></v-tabs-slider>

          <v-tab v-for="(t, i) in tabs" :key="i">
            <VLoginTab :t="t" :i="i" />
          </v-tab>
          <v-tab-item>
            <v-card class="pa-8">
              <v-card-text>
                <v-form ref="loginForm" v-model="validLogin" lazy-validation>
                  <LoginTabItem
                    :emailRules="emailRules"
                    :rules="rules"
                    :validLogin="validLogin"
                    :loginInfo="loginInfo"
                    @validateLogin="validateLogin"
                  />
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
          <v-tab-item>
            <v-card class="pa-8">
              <v-card-text>
                <v-form
                  ref="registerForm"
                  v-model="validRegister"
                  lazy-validation
                >
                  <RegisterTabItem
                    :emailRules="emailRules"
                    :rules="rules"
                    :phoneNumberRules="phoneNumberRules"
                    :validRegister="validRegister"
                    :newUser="newUser"
                    @validateRegister="validateRegister"
                  />
                </v-form>
              </v-card-text>
            </v-card>
          </v-tab-item>
        </v-tabs>
      </div>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data: () => ({
    tab: 0,
    validLogin: true,
    validRegister: true,
    loginInfo: {
      email: "",
      password: "",
    },
    newUser: {
      fn: "",
      ln: "",
      em: "",
      phone: "",
      passwd: "",
      getRmd: false,
    },
    tabs: [
      { name: "Login", icon: "mdi-account" },
      { name: "Register", icon: "mdi-account-outline" },
    ],
    emailRules: [
      (v) => !!v || "Required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
    phoneNumberRules: [
      (v) => !!v || "Required",
      (v) => /^0\d{9}$/.test(v) || "Phone number must be valid",
    ],
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => (v && v.length >= 8) || "Min 8 characters",
    },
  }),
  methods: {
    ...mapActions("account", [
      "setDialogLogin",
      "loadDataFromLogin",
      "register",
    ]),

    // Reset form
    resetForm() {
      this.$refs.loginForm.reset();
      this.$refs.loginForm.resetValidation();
      this.$refs.registerForm.reset();
      this.$refs.registerForm.resetValidation();
    },

    // Check form and then load data
    async validateLogin(loginInfo) {
      if (this.$refs.loginForm.validate()) {
        await this.loadDataFromLogin({ ...loginInfo });

        // Reset
        this.resetForm();
      }
    },

    async validateRegister(newUser) {
      if (this.$refs.registerForm.validate()) {
        newUser.getRmd = this.getGetReminders;
        // console.log(...newUser);
        await this.register({ ...newUser });

        // Reset
        this.resetForm();
      }
    },
  },
  components: {
    CloseLoginDlg: () => import("./CloseLoginDlg"),
    VLoginTab: () => import("./LoginTab"),
    LoginTabItem: () => import("./LoginTabItem"),
    RegisterTabItem: () => import("./RegisterTabItem"),
  },
  computed: {
    ...mapGetters("account", [
      "getInfoUser",
      "getDialogLogin",
      "getGetReminders",
    ]),
  },
};
</script>

<style lang="scss" scoped>
.v-slide-group__content {
  height: 50px;
}
</style>