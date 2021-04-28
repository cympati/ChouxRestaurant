<template>
  <div>
    <v-card class="pa-8">
      <v-card-text>
        <v-form ref="loginForm" v-model="validLogin" lazy-validation>
          <v-row>
            <v-col cols="12">
              <v-text-field
                v-model="loginInfo.email"
                :rules="emailRules"
                prepend-icon="mdi-email"
                label="E-mail"
                @keydown.enter="validateLogin"
                required
                class="my-0 py-0"
                height="28px"
                clearable
              ></v-text-field>
            </v-col>
            <v-col cols="12">
              <v-text-field
                v-model="loginInfo.password"
                :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.required, rules.min]"
                :type="show ? 'text' : 'password'"
                name="input-10-1"
                label="Password"
                prepend-icon="mdi-lock"
                hint="At least 8 characters"
                counter
                @keydown.enter="validateLogin"
                @click:append="show = !show"
                class="my-0 py-0"
                height="28px"
                clearable
              ></v-text-field>
            </v-col>
            <v-row class="d-flex justify-center align-center px-3">
              <v-col class="my-0 py-0" cols="12" sm="6" xsm="12">
                <a
                  class="textcl"
                  @click="dialogForgotPassword = !dialogForgotPassword"
                >
                  Forgot password?
                </a>
              </v-col>
              <v-spacer></v-spacer>
              <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
                <v-btn
                  block
                  :disabled="!validLogin"
                  color="success"
                  @click="validateLogin"
                >
                  Login
                </v-btn>
              </v-col>
            </v-row>
          </v-row>
        </v-form>
      </v-card-text>
    </v-card>
    <ForgotPassword
      :dialog="dialogForgotPassword"
      @closeDialog="dialogForgotPassword = false"
    />
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      show: false,
      validLogin: true,
      loginInfo: {
        email: "",
        password: "",
      },
      dialogForgotPassword: false,
    };
  },
  props: {
    infoDetails: Object,
    emailRules: Array,
    rules: Object,
    setLoginAttribute: Function,
  },
  components: {
    ForgotPassword: () => import("./ForgotPassword"),
  },
  methods: {
    ...mapActions("account", ["loadDataFromLogin"]),
    async validateLogin() {
      if (this.$refs.loginForm.validate()) {
        await this.loadDataFromLogin({ ...this.loginInfo });

        // Reset
        this.$refs.loginForm.reset();
        this.$refs.loginForm.resetValidation();
      }
    },
  },
};
</script>

<style  scoped>
.textcl {
  color: grey;
  font-weight: 400;
  padding-left: 6px;
}
</style>
