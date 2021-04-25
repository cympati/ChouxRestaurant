<template>
  <div>
    <v-dialog
      v-model="dialogLogin"
      max-width="620px"
      min-width="360px"
      persistent
    >
      <div>
        <CloseLoginDlg />
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
            <LoginTabItem
              :infoDetails="infoDetails"
              :emailRules="emailRules"
              :rules="rules"
              @setLoginAttribute="setLoginAttribute"
            />
          </v-tab-item>
          <v-tab-item>
            <RegisterTabItem
              :infoDetails="infoDetails"
              :emailRules="emailRules"
              :rules="rules"
              :phoneNumberRules="phoneNumberRules"
              @setLoginAttribute="setLoginAttribute"
            />
          </v-tab-item>
        </v-tabs>
      </div>
    </v-dialog>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  data: () => ({
    tab: 0,
    tabs: [
      { name: "Login", icon: "mdi-account" },
      { name: "Register", icon: "mdi-account-outline" },
    ],
    infoDetails: {
      // User
      firstName: "Patiphon",
      lastName: "Klangpraphan",
      id: "234",
      email: "cympati@gmail.com",
      password: "28052545", // verify password
      phoneNumber: "0956600463",
      isAdmin: 0,
    },

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
    ...mapActions("account", ["setDialogLogin"]),
    setLoginAttribute() {
      this.save();
      // this.$store.dispatch("setdialogLogin", true);
      this.setDialogLogin(false);
      console.log("complete");
    },
    save() {
      console.log("Information is saved!");
    },
  },
  components: {
    CloseLoginDlg: () => import("./CloseLoginDlg"),
    VLoginTab: () => import("./LoginTab"),
    LoginTabItem: () => import("./LoginTabItem"),
    RegisterTabItem: () => import("./RegisterTabItem"),
  },
  computed: {
    ...mapState("account", ["dialogLogin"]),
  },
};
</script>

<style lang="scss" scoped>
.v-slide-group__content {
  height: 50px;
}
</style>