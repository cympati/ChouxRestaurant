<template>
  <div>
    <v-dialog
      v-model="dialogConfirm"
      persistent
      max-width="400"
      transition="dialog-top-transition"
    >
      <v-card>
        <div class="d-flex align-center justify-center">
          <v-card-title class="headline"> Cofirm your password </v-card-title>
        </div>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="12" md="12">
                <v-text-field
                  block
                  v-model="confirmPassword"
                  :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[rules.required]"
                  :type="show ? 'text' : 'password'"
                  label="Confirm Password"
                  counter
                  @click:append="show = !show"
                  @keypress.enter="checkPasswordMatch"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="red lighten-1"
            text
            @click="$emit('changeDialogConfirm', false)"
          >
            Cancel
          </v-btn>

          <v-btn color="green darken-1" text @click="checkPasswordMatch">
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>

      <InvalidSnackbar
        :invalid="snackbarConfirmPasswordInvalid"
        :textInvalid="notificationTextConfirmPasswordInvalid"
        @changeInvalid="snackbarConfirmPasswordInvalid = false"
      />
    </v-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      show: false,
      myPassword: "28052545",
      confirmPassword: "",
      rules: {
        required: (value) => !!value || "Required.",
        min: (v) => (v && v.length >= 8) || "Min 8 characters",
      },
      snackbarConfirmPasswordInvalid: false,
      notificationTextConfirmPasswordInvalid: "Your password must match",
      // color: { color: "", value: "" },
    };
  },
  props: {
    // dialogMore: Number,
    dialogConfirm: Boolean,
    // colorSelect: Object,
    changeInfo: Function,
    // setColorBg: Function,
  },
  methods: {
    checkPasswordMatch() {
      this.confirmPassword === this.myPassword
        ? [
            (this.snackbarConfirmPasswordValid = true),
            (this.confirmPassword = ""),
            // this.$emit("changeDialogMore"),
            // (this.color = this.colorSelect),
            // this.$emit("setColorBg"),
            this.$emit("changeInfo"),
            this.$emit("changeDialogConfirm"),
          ]
        : [
            (this.snackbarConfirmPasswordInvalid = true),
            (this.confirmPassword = ""),
          ];
    },
  },
  components: {
    InvalidSnackbar: () => import("../Snackbars/InvalidSnackbar"),
  },
  computed: {
    // passwordMatch() {
    //   return () =>
    //     this.confirmPassword === this.myPassword || "Password must match";
    // },
  },
};
</script>

<style>
</style>