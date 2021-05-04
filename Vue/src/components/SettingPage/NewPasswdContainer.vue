<template>
  <v-row>
    <v-col cols="12" sm="6" md="6">
      <h4>New password</h4>
      <v-text-field
        v-model="newPassword"
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.min]"
        :type="show ? 'text' : 'password'"
        hint="At least 8 characters"
        counter
        @change="setNewPasswd(newPassword)"
        @click:append="show = !show"
      ></v-text-field>
    </v-col>
    <v-col cols="12" sm="6" md="6">
      <h4>Confirm new password</h4>
      <v-text-field
        v-model="confirmNewPassword"
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.min, newPasswordMatch]"
        :type="show ? 'text' : 'password'"
        hint="At least 8 characters"
        counter
        @change="setConfirmPasswd(confirmNewPassword)"
        @click:append="show = !show"
      ></v-text-field>
    </v-col>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      newPassword: "",
      confirmNewPassword: "",
      show: false,
    };
  },
  props: {
    rules: Object,
    setNewPasswd: Function,
    setConfirmPasswd: Function,
  },
  computed: {
    newPasswordMatch() {
      return () =>
        this.newPassword === this.confirmNewPassword ||
        "Confirm new password must match";
    },
  },
};
</script>

<style scoped>
h4 {
  font-weight: 400;
}
</style>