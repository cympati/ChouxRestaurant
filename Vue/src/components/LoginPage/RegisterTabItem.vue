<template>
  <v-row>
    <v-col cols="12" sm="6" md="6">
      <v-text-field
        v-model="newUser.fn"
        required
        :rules="[rules.required]"
        prepend-icon="mdi-account"
        label="Firstname"
        maxlength="25"
        @keydown.enter="validateRegister"
        class="my-0 py-0"
        height="28px"
      ></v-text-field>
    </v-col>
    <v-col cols="12" sm="6" md="6">
      <v-text-field
        v-model="newUser.ln"
        :rules="[rules.required]"
        prepend-icon="mdi-account-multiple-outline"
        label="Lastname"
        maxlength="25"
        required
        @keydown.enter="validateRegister"
        class="my-0 py-0"
        height="28px"
      ></v-text-field>
    </v-col>

    <v-col cols="12">
      <v-text-field
        v-model="newUser.em"
        :rules="emailRules"
        prepend-icon="mdi-email"
        label="Your email"
        required
        @keydown.enter="validateRegister"
        class="my-0 py-0"
        height="28px"
      ></v-text-field>
    </v-col>
    <v-col cols="12">
      <v-text-field
        v-model="newUser.phone"
        :rules="phoneNumberRules"
        prepend-icon="mdi-phone"
        label="Phone number"
        hint="0XXXXXXXXX"
        required
        maxlength="10"
        @keydown.enter="validateRegister"
        class="my-0 py-0"
        height="28px"
      ></v-text-field>
    </v-col>
    <v-col cols="12">
      <v-text-field
        v-model="newUser.passwd"
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, rules.min]"
        prepend-icon="mdi-lock"
        :type="show ? 'text' : 'password'"
        name="input-10-1"
        label="Password"
        hint="At least 8 characters"
        counter
        @click:append="show = !show"
        @keydown.enter="validateRegister"
        class="my-0 py-0"
        height="28px"
      ></v-text-field>
    </v-col>
    <v-col cols="12">
      <v-text-field
        block
        v-model="confirmPasswd"
        :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
        :rules="[rules.required, passwordMatch]"
        prepend-icon="mdi-lock-check"
        :type="show ? 'text' : 'password'"
        name="input-10-1"
        label="Confirm password"
        counter
        @click:append="show = !show"
        @keydown.enter="validateRegister"
        height="28px"
        class="my-0 py-0"
      ></v-text-field>
    </v-col>
    <v-col cols="12" class="my-0 pt-0">
      <div>
        <v-checkbox
          v-model="newUser.getRmd"
          class="my-1 line-height"
          @change="setGetReminders(newUser.getRmd)"
          ><template v-slot:label>
            <small
              >Yes, I want to get text updates and reminders about my
              reservations.</small
            >
          </template></v-checkbox
        >
      </div>
    </v-col>
    <v-spacer></v-spacer>
    <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
      <v-btn
        block
        :disabled="!validRegister"
        color="success"
        @click="$emit('validateRegister', newUser)"
        class="my-0"
        >Register</v-btn
      >
    </v-col>
  </v-row>
</template>

<script>
import { mapActions } from "vuex";

export default {
  data() {
    return {
      show: false,
      confirmPasswd: "",
    };
  },
  props: {
    emailRules: Array,
    phoneNumberRules: Array,
    rules: Object,
    validRegister: Boolean,
    newUser: Object,
    getGetReminders: Boolean,
  },
  methods: {
    ...mapActions("account", ["setGetReminders"]),
  },
  computed: {
    // Check password match confirm password or not
    passwordMatch() {
      return () =>
        this.newUser.passwd === this.confirmPasswd || "Password must match";
    },
  },
};
</script>

<style lang="scss" scoped>
.line-height {
  height: 25px;
}
</style>