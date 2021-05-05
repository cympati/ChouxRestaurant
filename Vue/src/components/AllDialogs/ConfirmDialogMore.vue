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
                  ref="confirmPasswordForm"
                  block
                  v-model="confirmPassword"
                  :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
                  :rules="[rules.requiredInfo, rules.min]"
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

          <v-btn color="red lighten-1" text @click="$emit('close', false)">
            Cancel
          </v-btn>

          <v-btn color="green darken-1" text @click="checkPasswordMatch">
            Confirm
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      show: false,
      confirmPassword: "",
    };
  },
  props: {
    checkConfirmPassword: Function,
    setMatch: Function,
    getMatch: Boolean,
    dialogConfirm: Boolean,
    rules: Object,
  },
  methods: {
    async checkPasswordMatch() {
      await this.checkConfirmPassword(this.confirmPassword);
      if (this.getMatch) {
        this.$refs.confirmPasswordForm.reset();
        this.$refs.confirmPasswordForm.resetValidation();
        this.$emit("changeInfo");
        this.$emit("close");
      } else {
        //
      }
    },
  },
};
</script>
