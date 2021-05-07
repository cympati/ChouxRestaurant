<template>
  <div>
    <v-dialog v-model="dialog" max-width="900px" min-width="500px" persistent>
      <v-card :loading="loading">
        <template slot="progress">
          <v-progress-linear
            color="blue"
            height="10"
            indeterminate
          ></v-progress-linear>
        </template>
        <VCardTitle :pd="paddingTitle" :cardTitle="cardTitle">
          <v-spacer></v-spacer>
          <small v-if="step > 1 && !resend">{{ minutes }}:{{ second }} </small>
          <small class="font-weight-medium">
            <a v-if="step > 1 && resend" @click="resendVerify"
              >Resend <v-icon>$resend</v-icon></a
            >
          </small>
        </VCardTitle>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col>
                <v-stepper v-model="step">
                  <v-stepper-header>
                    <v-stepper-step :complete="step > 1" step="1">
                      Enter your email
                    </v-stepper-step>

                    <v-divider></v-divider>

                    <v-stepper-step :complete="step > 2" step="2">
                      How to reset your password
                    </v-stepper-step>

                    <v-divider></v-divider>

                    <v-stepper-step step="3"> Set new password </v-stepper-step>
                  </v-stepper-header>

                  <v-stepper-items>
                    <v-stepper-content step="1">
                      <v-card-text color="white lighten-1" height="200px">
                        <v-container>
                          <v-form
                            ref="emailVerificationForm"
                            v-model="valid"
                            lazy-validation
                          >
                            <v-row>
                              <v-col cols="12" sm="12" md="12">
                                <p>
                                  We'll send you a link to reset your password.
                                </p>
                                <v-text-field
                                  ref="emailVerificationForm"
                                  label="Your email"
                                  required
                                  clearable
                                  requiredInfo
                                  :rules="emailRules"
                                  v-model="email"
                                  @keydown.enter="
                                    validateVerificationPasswordForm()
                                  "
                                ></v-text-field>
                              </v-col>
                            </v-row>
                          </v-form>
                        </v-container>
                      </v-card-text>

                      <v-card-actions>
                        <v-btn text @click="backToLogin"> Back </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="primary"
                          @click="validateEmailVertificationForm()"
                          :disabled="!valid"
                        >
                          Continue
                        </v-btn>
                      </v-card-actions>
                    </v-stepper-content>

                    <v-stepper-content step="2">
                      <v-card-text color="white lighten-1" height="200px">
                        <v-container>
                          <v-row>
                            <v-col cols="12" sm="12" md="12">
                              <p class="text-center">
                                Check verification code in your email
                              </p>
                            </v-col>
                          </v-row>
                        </v-container>
                      </v-card-text>

                      <v-card-actions>
                        <v-btn text @click="resetPassForm()"> Back </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn color="primary" @click="step = 3">
                          Continue
                        </v-btn>
                      </v-card-actions>
                    </v-stepper-content>

                    <v-stepper-content step="3">
                      <v-card-text color="white lighten-1">
                        <v-container class="container-width">
                          <v-form ref="verificationPasswordForm">
                            <v-row>
                              <v-col cols="12" sm="12" md="12">
                                <v-text-field
                                  label="New password"
                                  v-model="newPassword"
                                  required
                                  clearable
                                  requiredInfo
                                  :append-icon="
                                    show ? 'mdi-eye' : 'mdi-eye-off'
                                  "
                                  :rules="[
                                    passwordRules.required,
                                    passwordRules.min,
                                  ]"
                                  :type="show ? 'text' : 'password'"
                                  name="input-10-1"
                                  hint="At least 8 characters"
                                  counter
                                  @click:append="show = !show"
                                ></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="12" md="12">
                                <v-text-field
                                  label="Confirm your new password"
                                  v-model="confirmNewPassword"
                                  required
                                  clearable
                                  requiredInfo
                                  :append-icon="
                                    show ? 'mdi-eye' : 'mdi-eye-off'
                                  "
                                  :type="show ? 'text' : 'password'"
                                  counter
                                  :rules="[newPasswordMatch]"
                                  @click:append="show = !show"
                                ></v-text-field>
                              </v-col>
                              <v-col cols="12" sm="12" md="12">
                                <v-text-field
                                  label="Verification code"
                                  v-model="yourVerifyCode"
                                  required
                                  clearable
                                  requiredInfo
                                ></v-text-field>
                              </v-col>
                            </v-row>
                          </v-form>
                        </v-container>
                      </v-card-text>

                      <v-card-actions>
                        <v-btn text @click="step = 2"> Back </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="primary"
                          :disabled="!validVerificationPassword"
                          @click="validateVerificationPasswordForm()"
                        >
                          Confirm
                        </v-btn>
                      </v-card-actions>
                    </v-stepper-content>
                  </v-stepper-items>
                </v-stepper>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import axios from "../../axios/axios";
export default {
  data() {
    return {
      email: "",
      emailCone: "",
      loading: false,
      paddingTitle: "px-9 ",
      cardTitle: "Forgot Password",
      show: false,
      notificationTextInvalid: "Your verification code must be valid!",
      notificationTextPasswordInvalid: "New password is required",
      validVerificationPassword: true,
      valid: true,
      snackbarInvalid: false,
      snackbarPasswordInvalid: false,
      yourVerifyCode: "",
      verificationCode: "1234",
      confirmNewPassword: "",
      newPassword: "",
      step: 1,
      minutes: "05",
      second: "00",
      resend: false,
      interval: null,
      emailRules: [
        (v) => !!v || "Required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ],
      passwordRules: {
        required: (value) => !!value || "Required.",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
    };
  },
  components: {
    VCardTitle: () => import("../JubJibComponent/VCardTitle"),
  },
  props: {
    dialog: Boolean,
  },
  computed: {
    ...mapGetters("account", [
      "getIsLogin",
      "getInvalidSnb",
      "getInfoUser",
      "getResetId",
      "getEmailValid",
    ]),
    newPasswordMatch() {
      return () =>
        this.newPassword === this.confirmNewPassword || "Password must match";
    },
  },
  methods: {
    ...mapActions("account", [
      "setDialogLogin",
      "setValidSnb",
      "setInvalidSnb",
      "forgot",
      "reset",
    ]),
    async reserveLoading() {
      this.loading = true;
      await new Promise((res) => {
        setTimeout(
          () => [res(), (this.loading = false), this.timeCount()],
          2000
        );
      });
    },

    timeCount() {
      this.resend = false;
      let totalTime = 300; // 5min
      if (totalTime > 0) {
        this.interval = setInterval(() => {
          this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
          totalTime -= 1;
          this.minutes = "0" + Math.floor(totalTime / 60).toFixed(0);
          this.second = ("0" + (totalTime % 60)).slice(-2);
          if (totalTime <= 0) {
            clearInterval(this.interval);
            this.minutes = "05";
            this.second = "00";
            this.resend = true;
          }
        }, 1000);
      }
    },
    resetEmailForm() {
      this.$refs.emailVerificationForm.reset();
      this.$refs.emailVerificationForm.resetValidation();
    },
    resetPassForm() {
      this.step = 1;
      clearInterval(this.interval);
      this.minutes = "05";
      this.second = "00";
      this.$refs.verificationPasswordForm.reset();
      this.$refs.verificationPasswordForm.resetValidation();
    },
    async validateEmailVertificationForm() {
      if (this.$refs.emailVerificationForm.validate()) {
        await this.forgot(this.email);
        if (this.getEmailValid) {
          await this.reserveLoading();
          this.step = 2;
          this.emailCone = this.email;
          this.resetEmailForm();
        }
      } else {
        //
      }
    },

    async validateVerificationPasswordForm() {
      if (this.$refs.verificationPasswordForm.validate()) {
        await axios
          .patch("/auth/reset", {
            id_reset: this.getResetId,
            new_passwd: this.newPassword,
            verify: this.yourVerifyCode,
          })
          .then((response) => {
            const snackbar = {
              dialog: true,
              text: response.data.text,
            };
            if (response.data.success) {
              this.resetEmailForm();
              this.resetPassForm();
              this.$emit("closeDialog");
              this.setDialogLogin(false);
              this.setValidSnb(snackbar);
            } else {
              this.resetEmailForm();
              this.resetPassForm();
              this.$emit("closeDialog");
              this.setDialogLogin(false);
              this.setInvalidSnb(snackbar);
            }
          })
          .catch((error) => console.log(error));
      }
    },
    backToLogin() {
      this.resetEmailForm(), this.$emit("closeDialog");
    },
    async resendVerify() {
      await this.forgot(this.emailCone);
      if (this.getEmailValid) {
        await this.reserveLoading();
      } else {
        //
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.container-width {
  max-width: 500px;
}
</style>